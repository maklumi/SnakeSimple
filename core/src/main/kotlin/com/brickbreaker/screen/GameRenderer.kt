package com.brickbreaker.screen

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.viewport.FitViewport
import com.brickbreaker.config.GameConfig
import com.util.ViewportUtils
import com.util.debug.DebugCameraController
import ktx.app.clearScreen

class GameRenderer {

    private val camera = OrthographicCamera()
    private val viewport = FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera)
    private val renderer = ShapeRenderer()

    fun render(delta: Float) {
        DebugCameraController.handleDebugInput(delta)
        DebugCameraController.applyTo(camera)

        clearScreen(0f, 0f, 0f)
        renderDebug()
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
        renderer.circle(GameConfig.WORLD_CENTER_X, GameConfig.WORLD_CENTER_Y, 3f)
        renderer.color = oldColor
    }

    fun resize(width: Int, height: Int) {
        viewport.update(width, height)
        ViewportUtils.debugPixelPerUnit(viewport)
    }

    fun dispose() {
        renderer.dispose()
    }

}