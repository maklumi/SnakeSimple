package com.brickbreaker.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.viewport.FitViewport
import com.brickbreaker.assets.AssetDescriptors
import com.brickbreaker.assets.RegionNames
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

    private val backgroundRegion = RegionNames.background()
    private val paddle = controller.paddle
    private val ball = controller.ball
    private val bricks = controller.bricks
    private var isDrawGrid = false
    private var isDrawDebug = false

    fun render(delta: Float) {
        DebugCameraController.handleDebugInput(delta)
        DebugCameraController.applyTo(camera)
        if (Gdx.input.isKeyJustPressed(Input.Keys.F5)) isDrawGrid = !isDrawGrid
        if (Gdx.input.isKeyJustPressed(Input.Keys.F6)) isDrawDebug = !isDrawDebug
        clearScreen(0f, 0f, 0f)
        renderGamePlay()
        renderHud()
        renderDebug()
    }

    private fun renderGamePlay() {
        viewport.apply()
        batch.projectionMatrix = camera.combined
        batch.begin()
        drawGamePlay()
        batch.end()
    }

    private fun drawGamePlay() {
        batch.draw(backgroundRegion, 0f, 0f, GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT)
        batch.draw(RegionNames.paddle(), paddle.x, paddle.y, paddle.width, paddle.height)
        batch.draw(RegionNames.ball(), ball.x, ball.y, ball.width, ball.height)
        bricks.forEach { brick -> batch.draw(RegionNames.brick(), brick.x, brick.y, brick.width, brick.height) }
        controller.effects.forEach { effect -> effect.draw(batch) }
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
        if (isDrawGrid)
            ViewportUtils.drawGrid(viewport, renderer, 4)

        renderer.projectionMatrix = camera.combined
        renderer.begin(ShapeRenderer.ShapeType.Line)
        if (isDrawDebug)
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