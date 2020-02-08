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


class GameRenderer(private val gameWorld: GameWorld, val batch: SpriteBatch, assetManager: AssetManager) {

    private val camera = OrthographicCamera()
    private val viewport = FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera)
    private val shapeRenderer = ShapeRenderer()

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

        renderGamePlay()
        renderDebug()
    }

    fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
        ViewportUtils.debugPixelPerUnit(viewport)
    }

    fun screenToWorld(screenCoordinates: Vector2): Vector2 = viewport.unproject(screenCoordinates)

    fun dispose() {
        shapeRenderer.dispose()
        mapRenderer.dispose()
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