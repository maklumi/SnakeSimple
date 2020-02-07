package com.platformer.screen.game

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.FitViewport
import com.platformer.assets.AssetDescriptors
import com.platformer.config.GameConfig
import com.platformer.config.GameConfig.UNIT_SCALE
import com.util.GdxUtils
import com.util.ViewportUtils
import com.util.debug.DebugCameraController
import com.util.debug.ShapeRendererUtils


class GameRenderer(val game: GameWorld, val batch: SpriteBatch, assetManager: AssetManager) {

    private val camera = OrthographicCamera()
    private val viewport = FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera)
    private val renderer = ShapeRenderer()

    private val map = assetManager[AssetDescriptors.LEVEL_01]
    private val mapRenderer = OrthogonalTiledMapRenderer(map, UNIT_SCALE, batch)

    fun update(delta: Float) {
        DebugCameraController.apply {
            handleDebugInput(delta)
            applyTo(camera)
        }

        GdxUtils.clearScreen()

        mapRenderer.apply {
            setView(camera) // internally sets project matrix, important to call
            render() // internally calls begin()/end() from SpriteBatch
        }

        renderDebug()
    }

    fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
        ViewportUtils.debugPixelPerUnit(viewport)
    }

    fun screenToWorld(screenCoordinates: Vector2): Vector2 = viewport.unproject(screenCoordinates)

    fun dispose() {
        renderer.dispose()
        mapRenderer.dispose()
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
        renderer.color = Color.GOLD

        ShapeRendererUtils.entities(renderer, game.waterHazards)
    }
}