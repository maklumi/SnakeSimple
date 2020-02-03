package com.jumper.screen

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.viewport.FitViewport
import com.jumper.config.GameConfig
import com.util.ViewportUtils
import com.util.debug.DebugCameraController
import ktx.app.clearScreen

class GameRenderer(controller: GameController) {

    private val camera = OrthographicCamera()
    private val viewport = FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera)
    private var renderer = ShapeRenderer()

    private val planet = controller.planet
    private val monster = controller.monster

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
        // planet
        renderer.color = Color.PURPLE
        val bounds = planet.bounds
        renderer.circle(bounds.x, bounds.y, bounds.radius, 30)

        // monster
        renderer.color = Color.CORAL
        val mb = monster.bounds
        renderer.rect(mb.x, mb.y, 0f, 0f, mb.width, mb.height, 1f, 1f, GameConfig.MONSTER_START_ANG_SPEED - monster.angleDeg)
    }

    fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
        ViewportUtils.debugPixelPerUnit(viewport)
    }

    fun dispose() {
        renderer.dispose()
    }

}