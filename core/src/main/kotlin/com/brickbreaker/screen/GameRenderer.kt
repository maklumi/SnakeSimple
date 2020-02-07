package com.brickbreaker.screen

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.viewport.FitViewport
import com.brickbreaker.assets.AssetDescriptors
import com.brickbreaker.config.GameConfig
import com.util.ViewportUtils
import com.util.debug.DebugCameraController
import com.util.debug.ShapeRendererUtils
import ktx.app.clearScreen

class GameRenderer(private val controller: GameController,
                   private val batch: SpriteBatch,
                   assetManager: AssetManager) {

    private val hudViewport = FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT)
    private val glyphLayout = GlyphLayout()
    private var bitmapFont = assetManager.get(AssetDescriptors.FONT)

    private val camera = OrthographicCamera()
    private val viewport = FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera)
    private val renderer = ShapeRenderer()

    fun render(delta: Float) {
        DebugCameraController.handleDebugInput(delta)
        DebugCameraController.applyTo(camera)

        clearScreen(0f, 0f, 0f)
        renderHud()
        renderDebug()
    }

    private fun renderHud() {
        hudViewport.apply()
        batch.projectionMatrix = hudViewport.camera.combined
        batch.begin()
        drawHud()
        batch.end()
    }

    private fun drawHud() {
        glyphLayout.setText(bitmapFont, "SCORE: " + controller.scoreController.score)
        bitmapFont.draw(batch, glyphLayout, 20f, GameConfig.HUD_HEIGHT - glyphLayout.height)
    }

    private fun renderDebug() {
        ViewportUtils.drawGrid(viewport, renderer, 4)

        renderer.projectionMatrix = camera.combined
        renderer.begin(ShapeRenderer.ShapeType.Line)
        drawDebug()
        renderer.end()
    }

    private fun drawDebug() {
        val oldColor = renderer.color.cpy()
        renderer.color = Color.GOLDENROD
        // paddle
        val paddleBound = controller.paddle.bounds
        ShapeRendererUtils.polygon(renderer, paddleBound)
        // bricks
        controller.bricks.forEach { ShapeRendererUtils.polygon(renderer, it.bounds) }
        // ball
        ShapeRendererUtils.circle(renderer, controller.ball.bound)
        renderer.color = oldColor
    }

    fun resize(width: Int, height: Int) {
        viewport.update(width, height)
        hudViewport.update(width, height, true)
        ViewportUtils.debugPixelPerUnit(viewport)
        ViewportUtils.debugPixelPerUnit(hudViewport)
    }

    fun dispose() {
        renderer.dispose()
    }

}