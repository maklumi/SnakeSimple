package com.jumper.screen

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.viewport.FitViewport
import com.jumper.config.GameConfig
import com.util.ViewportUtils
import com.util.debug.DebugCameraController
import ktx.app.clearScreen

class GameRenderer {

    private val camera = OrthographicCamera()
    private val viewport = FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera)
    private var renderer = ShapeRenderer()

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

        renderer.circle(GameConfig.WORLD_CENTER_X, GameConfig.WORLD_CENTER_Y, 2.5f, 10)
    }

    fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
    }

    fun dispose() {
        renderer.dispose()
    }

}