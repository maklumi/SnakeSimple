package com.jumper.screen

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.viewport.FitViewport
import com.jumper.assets.AssetDescriptors
import com.jumper.assets.RegionNames
import com.jumper.common.GameManager
import com.jumper.config.GameConfig
import com.util.ViewportUtils
import com.util.debug.DebugCameraController
import com.util.game.GameBase
import ktx.app.clearScreen

class GameRenderer(private val controller: GameController, game: GameBase) {

    private val camera = OrthographicCamera()
    private val viewport = FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera)
    private var renderer = ShapeRenderer()

    private val batch = game.batch
    private val assetManager = game.assetManager
    private val font = assetManager.get(AssetDescriptors.FONT)
    private val hudCamera = OrthographicCamera()
    private val hudViewport = FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT, hudCamera)
    private val glyphLayout = GlyphLayout()

    private val planet = controller.planet
    private val monster = controller.monster
    private val obstacles = controller.obstacles

    // adding textures
    private val atlas = assetManager.get(AssetDescriptors.GAME_PLAY)
    private val backgroundRegion = atlas.findRegion(RegionNames.BACKGROUND)
    private val planetRegion = atlas.findRegion(RegionNames.PLANET)
    // adding animations
    private val obstacleAnimation = Animation(0.1f, atlas.findRegions(RegionNames.OBSTACLE), PlayMode.LOOP_PINGPONG)
    private val coinAnimation = Animation(0.2f, atlas.findRegions(RegionNames.COIN), PlayMode.LOOP_PINGPONG)
    private val monsterAnimation = Animation(0.05f, atlas.findRegions(RegionNames.PLAYER), PlayMode.LOOP_PINGPONG)
    private var animationTime = 0f

    init {
        DebugCameraController.setStartPosition(GameConfig.WORLD_CENTER_X, GameConfig.WORLD_CENTER_Y)
    }

    fun render(delta: Float) {
        DebugCameraController.handleDebugInput(delta)
        DebugCameraController.applyTo(camera)

        clearScreen(0f, 0f, 0f)
        renderGamePlay(delta)
        renderUI()
        renderDebug()
    }

    private fun renderGamePlay(delta: Float) {
        viewport.apply()
        batch.projectionMatrix = camera.combined
        batch.begin()
        drawGamePlay(delta)
        batch.end()
    }

    private fun drawGamePlay(delta: Float) {
        animationTime += delta

        // background
        batch.draw(backgroundRegion, 0f, 0f, GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT)
        // obstacles
        val obstacleRegion = obstacleAnimation.getKeyFrame(animationTime)
        val obstacles = controller.obstacles
        obstacles.forEach { obstacle ->
            batch.draw(obstacleRegion,
                       obstacle.x, obstacle.y, 0f, 0f,
                       obstacle.width, obstacle.height, 1.0f, 1.0f,
                       GameConfig.START_ANGLE - obstacle.angleDeg
            )
        }
        // planet
        val planet = controller.planet
        batch.draw(planetRegion, planet.x, planet.y, planet.width, planet.height)
        // coins
        val coins = controller.coins
        val coinRegion: TextureRegion = coinAnimation.getKeyFrame(animationTime)
        coins.forEach { coin ->
            batch.draw(coinRegion,
                       coin.x, coin.y, 0f, 0f,
                       coin.width, coin.height,
                       coin.scale, coin.scale,
                       GameConfig.START_ANGLE - coin.angleDeg
            )
        }
        // monster
        val monster = controller.monster
        val monsterRegion: TextureRegion = monsterAnimation.getKeyFrame(animationTime)
        batch.draw(monsterRegion,
                   monster.x, monster.y, 0f, 0f,
                   monster.width, monster.height,
                   1.0f, 1.0f,
                   GameConfig.START_ANGLE - monster.angleDeg
        )
    }

    private fun renderUI() {
        hudViewport.apply() // because using multiple viewports
        batch.projectionMatrix = hudCamera.combined
        batch.begin()

        glyphLayout.setText(font, "HIGH SCORE: ${GameManager.displayHighScore}")
        font.draw(batch, glyphLayout, 20f, GameConfig.HUD_HEIGHT - glyphLayout.height)

        glyphLayout.setText(font, "SCORE: ${GameManager.displayScore}")
        font.draw(batch, glyphLayout, GameConfig.HUD_WIDTH - glyphLayout.width - 20f,
                  GameConfig.HUD_HEIGHT - glyphLayout.height)


        val startWaitTimer = controller.startWaitTimer

        if (startWaitTimer >= 0) {
            val waitTime = startWaitTimer.toInt()
            val waitTimeString = if (waitTime == 0) "GO!" else "$waitTime"
            glyphLayout.setText(font, waitTimeString)
            font.draw(batch, glyphLayout,
                      (GameConfig.HUD_WIDTH - glyphLayout.width) / 2f,
                      (GameConfig.HUD_HEIGHT + glyphLayout.height) / 2f
            )
        }

        batch.end()
    }

    private fun renderDebug() {
        ViewportUtils.drawGrid(viewport, renderer)

        val oldColor = Color(renderer.color)
        viewport.apply()
        renderer.projectionMatrix = camera.combined
        renderer.begin(ShapeRenderer.ShapeType.Line)

        drawDebug()

        renderer.end()
        renderer.color = oldColor
    }

    private fun drawDebug() {
        // planet
        renderer.color = Color.PURPLE
        val bounds = planet.bounds
        renderer.circle(bounds.x, bounds.y, bounds.radius, 30)

        // monster
        renderer.color = Color.CORAL
        val mb = monster.bounds
        renderer.rect(mb.x, mb.y, 0f, 0f, mb.width, mb.height, 1f, 1f, GameConfig.MONSTER_START_ANG_SPEED - monster.angleDeg)

        // coins
        val coins = controller.coins
        coins.forEach { c ->
            renderer.color = Color.YELLOW
            renderer.rect(c.bounds.x, c.bounds.y, 0f, 0f,
                          c.bounds.width, c.bounds.height, c.scale, c.scale,
                          GameConfig.START_ANGLE - c.angleDeg)
        }

        // obstacles
        obstacles.forEach { obstacle ->
            renderer.color = Color.GREEN
            // obstacle
            val ob = obstacle.bounds
            renderer.rect(ob.x, ob.y, 0f, 0f, ob.width, ob.height, 1f, 1f,
                          GameConfig.START_ANGLE - obstacle.angleDeg)

            // sensor
            renderer.color = Color.RED
            val sb = obstacle.sensor
            renderer.rect(sb.x, sb.y, 0f, 0f, sb.width, sb.height, 1f, 1f,
                          GameConfig.START_ANGLE - obstacle.angleDeg)
        }
    }

    fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
        hudViewport.update(width, height, true)
        ViewportUtils.debugPixelPerUnit(viewport)
    }

    fun dispose() {
        renderer.dispose()
    }

}