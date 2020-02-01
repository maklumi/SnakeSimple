package com.snakesimple.screen

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.viewport.FitViewport
import com.snakesimple.config.GameConfig
import com.snakesimple.util.ViewportUtils
import com.snakesimple.util.debug.DebugCameraController
import ktx.app.clearScreen

class GameRenderer(private val controller: GameController) : Disposable {

    private val camera = OrthographicCamera()
    private val viewport = FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera)
    private var renderer: ShapeRenderer = ShapeRenderer()

    init {
        DebugCameraController.setStartPosition(GameConfig.WORLD_CENTER_X, GameConfig.WORLD_CENTER_Y)
    }

    fun render(delta: Float) {
        DebugCameraController.handleDebugInput(delta)
        DebugCameraController.applyTo(camera)

        clearScreen(0f, 0f, 0f)

        renderDebug()
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
        renderer.rect(hb.x, hb.y, hb.width, hb.height)

        renderer.color = Color.FIREBRICK
        val cb = controller.coin.bounds
        renderer.rect(cb.x, cb.y, cb.width, cb.height)

        renderer.color = Color.GOLDENROD
        val bodyParts = controller.snake.bodyParts
        bodyParts.forEach { part ->
            renderer.rect(part.x, part.y, part.width, part.height)
        }
    }

    fun resize(width: Int, height: Int) {
        viewport.update(width, height)
        ViewportUtils.debugPixelPerUnit(viewport)
    }

    override fun dispose() {
        renderer.dispose()
    }
}