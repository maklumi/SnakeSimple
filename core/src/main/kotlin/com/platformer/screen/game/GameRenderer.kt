package com.platformer.screen.game

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.FitViewport
import com.platformer.assets.AssetDescriptors
import com.platformer.assets.RegionNames
import com.platformer.config.GameConfig
import com.platformer.config.GameConfig.UNIT_SCALE
import com.util.GdxUtils
import com.util.ViewportUtils
import com.util.debug.DebugCameraController
import com.util.debug.ShapeRendererUtils


class GameRenderer(private val gameWorld: GameWorld, val batch: SpriteBatch, assetManager: AssetManager) {

    private val camera = OrthographicCamera()
    private val viewport = FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera)
    private val shapeRenderer = ShapeRenderer()

    private val map = assetManager[AssetDescriptors.LEVEL_01]
    private val mapRenderer = OrthogonalTiledMapRenderer(map, UNIT_SCALE, batch)

    private val hudViewport = FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT)
    private val padding = 40f
    private val whiteHalfTransparent = Color(1f, 1f, 1f, 0.5f)
    private val layout = GlyphLayout()
    private val lifeRegion = RegionNames.life()
    private val font = assetManager[AssetDescriptors.FONT]
    private val backgroundTexture = assetManager[AssetDescriptors.BACKGROUND]

    fun update(delta: Float) {
        DebugCameraController.apply {
            handleDebugInput(delta)
            applyTo(camera)
        }

        GdxUtils.clearScreen()

        renderBackground()

        mapRenderer.apply {
            setView(camera) // internally sets project matrix, important to call
            render() // internally calls begin()/end() from SpriteBatch
        }

        renderGamePlay()
        renderHud()
        renderDebug()
    }

    fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
        hudViewport.update(width, height, true)
        ViewportUtils.debugPixelPerUnit(viewport)
        ViewportUtils.debugPixelPerUnit(hudViewport)
    }

    fun screenToWorld(screenCoordinates: Vector2): Vector2 = viewport.unproject(screenCoordinates)

    fun dispose() {
        shapeRenderer.dispose()
        mapRenderer.dispose()
    }

    private fun renderBackground() {
        viewport.apply()
        batch.projectionMatrix = camera.combined
        batch.begin()
        batch.draw(backgroundTexture, 0f, 0f, GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT)
        batch.end()
    }

    private fun renderGamePlay() {
        viewport.apply()
        batch.projectionMatrix = camera.combined
        batch.begin()

        val player = gameWorld.player
        batch.draw(player.region, player.x, player.y, player.width, player.height)

        val coins = gameWorld.coins
        coins.forEach { c -> batch.draw(c.region, c.x, c.y, c.width, c.height) }

        batch.end()
    }

    private fun renderHud() {
        hudViewport.apply()
        val oldColor = batch.color.cpy()
        batch.projectionMatrix = hudViewport.camera.combined
        batch.begin()

        drawHud()

        batch.end()
        batch.color = oldColor
    }

    private fun drawHud() {
        // score
        val scoreString = "Score: ${gameWorld.score}"
        layout.setText(font, scoreString)

        val scoreY = GameConfig.HUD_HEIGHT - layout.height
        font.draw(batch, layout, padding, scoreY)

        // lives
        val offsetX = GameConfig.LIVES_START * (GameConfig.LIFE_WIDTH + GameConfig.LIFE_SPACING)
        val offsetY = GameConfig.LIFE_HEIGHT + GameConfig.LIFE_SPACING
        val startX = GameConfig.HUD_WIDTH - offsetX
        val startY = GameConfig.HUD_HEIGHT - offsetY

        for (i in 0..GameConfig.LIVES_START) {
            if (gameWorld.lives <= i) batch.color = whiteHalfTransparent

            val x = startX + i * (GameConfig.LIFE_WIDTH + GameConfig.LIFE_SPACING)
            batch.draw(lifeRegion, x, startY, GameConfig.LIFE_WIDTH, GameConfig.LIFE_HEIGHT)
        }

    }

    private fun renderDebug() {
        if (gameWorld.isDrawGrid) ViewportUtils.drawGrid(viewport, shapeRenderer)
        if (!gameWorld.isDrawDebug) return

        val oldColor = shapeRenderer.color.cpy()

        viewport.apply()
        shapeRenderer.apply {
            projectionMatrix = camera.combined
            begin(ShapeRenderer.ShapeType.Line)
        }

        drawDebug()

        shapeRenderer.apply {
            end()
            color = oldColor
        }
    }

    private fun drawDebug() {
        shapeRenderer.color = Color.ROYAL
        ShapeRendererUtils.entities(shapeRenderer, gameWorld.waterHazards)
        shapeRenderer.color = Color.GREEN
        ShapeRendererUtils.entities(shapeRenderer, gameWorld.platforms)
        shapeRenderer.color = Color.GOLD
        ShapeRendererUtils.polygon(shapeRenderer, gameWorld.player.bounds)
        shapeRenderer.color = Color.FIREBRICK
        ShapeRendererUtils.entities(shapeRenderer, gameWorld.coins)
    }
}