package com.platformer.screen.game

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.FitViewport
import com.platformer.config.GameConfig
import com.util.GdxUtils
import com.util.ViewportUtils
import com.util.debug.DebugCameraController


class GameRenderer(val game: GameWorld, val batch: SpriteBatch) {

    private val camera = OrthographicCamera()
    private val viewport = FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera)
    private val renderer = ShapeRenderer()

    fun update(delta: Float) {
        DebugCameraController.apply {
            handleDebugInput(delta)
            applyTo(camera)
        }

        GdxUtils.clearScreen()

        renderDebug()
    }

    fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
        ViewportUtils.debugPixelPerUnit(viewport)
    }

    fun screenToWorld(screenCoordinates: Vector2): Vector2 = viewport.unproject(screenCoordinates)

    fun dispose() {
        renderer.dispose()
    }

    private fun renderDebug() {
        if (game.isDrawGrid) ViewportUtils.drawGrid(viewport, renderer)
        if (!game.isDrawDebug) return

        val oldColor = renderer.color.cpy()

        viewport.apply()
        renderer.apply {
            projectionMatrix = camera.combined
            begin(ShapeRenderer.ShapeType.Line)
        }

        drawDebug()

        renderer.apply {
            end()
            color = oldColor
        }
    }

    private fun drawDebug() {
        renderer.apply {
            color = Color.GOLD
            circle(GameConfig.WORLD_CENTER_X, GameConfig.WORLD_CENTER_Y, 2.0f, 16)
        }
    }
}