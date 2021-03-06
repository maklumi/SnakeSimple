package com.snakesimple.screen

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.viewport.FitViewport
import com.snakesimple.assets.Descriptor
import com.snakesimple.assets.RegionNames
import com.snakesimple.common.GameManager
import com.snakesimple.config.GameConfig
import com.util.ViewportUtils
import com.util.debug.DebugCameraController
import ktx.app.clearScreen

class GameRenderer(private val batch: SpriteBatch, private val controller: GameController, assetManager: AssetManager) : Disposable {

    private val camera = OrthographicCamera()
    private val viewport = FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera)
    private var renderer: ShapeRenderer = ShapeRenderer()

    private val uiFont = assetManager.get(Descriptor.UI_FONT)
    private val hudCamera = OrthographicCamera()
    private val hudViewport = FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT, hudCamera)
    private val glyphLayout = GlyphLayout()

    private val gamePlayAtlas = assetManager.get(Descriptor.GAME_PLAY)
    private val bg = gamePlayAtlas.findRegion(RegionNames.BACKGROUND)
    private val headRegion = gamePlayAtlas.findRegion(RegionNames.HEAD)
    private val partRegion = gamePlayAtlas.findRegion(RegionNames.BODY)
    private val coinRegion = gamePlayAtlas.findRegion(RegionNames.COIN)
    private val coin = controller.coin

    init {
        DebugCameraController.setStartPosition(GameConfig.WORLD_CENTER_X, GameConfig.WORLD_CENTER_Y)
    }

    fun render(delta: Float) {
        DebugCameraController.handleDebugInput(delta)
        DebugCameraController.applyTo(camera)

        clearScreen(0f, 0f, 0f)

        renderGamePlay()
        renderUI()
        GameManager.displayScores(delta)
//        renderDebug()
    }

    private fun renderGamePlay() {
        viewport.apply()
        batch.projectionMatrix = camera.combined
        batch.begin()

        drawGamePlay()

        batch.end()
    }

    private fun drawGamePlay() {
        // background
        batch.draw(bg, 0f, 0f, GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT)
        // coin
        batch.draw(coinRegion, coin.x, coin.y, coin.width, coin.height)
        // snake head and body parts
        val snake = controller.snake
        snake.bodyParts.forEach { part ->
            batch.draw(partRegion, part.x, part.y, part.width, part.height)
        }
        val head = snake.head
        batch.draw(headRegion, head.x, head.y, head.width, head.height)
    }

    private fun renderUI() {
        hudViewport.apply() // because using multiple viewports
        batch.projectionMatrix = hudCamera.combined
        batch.begin()

        glyphLayout.setText(uiFont, "HIGH SCORE: ${GameManager.displayHighScore}")
        uiFont.draw(batch, glyphLayout, 20f, GameConfig.HUD_HEIGHT - glyphLayout.height)

        glyphLayout.setText(uiFont, "SCORE: ${GameManager.displayScore}")
        uiFont.draw(batch, glyphLayout, GameConfig.HUD_WIDTH - glyphLayout.width - 20f,
                    GameConfig.HUD_HEIGHT - glyphLayout.height)

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
        renderer.color = Color.PURPLE

        val head = controller.snake.head
        val hb = head.bounds
        renderer.rect(hb.x, hb.y, head.width, head.height)

        renderer.color = Color.FIREBRICK
        val cb = controller.coin.bounds
        renderer.rect(cb.x, cb.y, controller.coin.width, controller.coin.height)

        renderer.color = Color.GOLDENROD
        val bodyParts = controller.snake.bodyParts
        bodyParts.forEach { part ->
            renderer.rect(part.x, part.y, part.width, part.height)
        }
    }

    fun resize(width: Int, height: Int) {
        viewport.update(width, height)
        hudViewport.update(width, height, true)
        ViewportUtils.debugPixelPerUnit(viewport)
        ViewportUtils.debugPixelPerUnit(hudViewport)
    }

    override fun dispose() {
        renderer.dispose()
    }
}