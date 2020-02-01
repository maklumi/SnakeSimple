package com.snakesimple.screen

import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.Logger
import com.badlogic.gdx.utils.viewport.FitViewport
import com.snakesimple.SimpleSnakeMain
import com.snakesimple.assets.Descriptor
import com.snakesimple.config.GameConfig
import ktx.app.clearScreen


class LoadingScreen(private val game: SimpleSnakeMain) : ScreenAdapter() {

    private val assetManager = game.assetManager
    private val camera = OrthographicCamera()
    private val viewport = FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT, camera)
    private val renderer = ShapeRenderer()
    private var progress = 0f
    private var waitTime = 0.75f
    private var changeScreen = false

    override fun show() {
//        assetManager.logger = Logger("LoadingScreen", Logger.DEBUG)
        assetManager.load(Descriptor.UI_FONT)
        assetManager.load(Descriptor.GAME_PLAY)
        assetManager.load(Descriptor.UI_SKIN)
        assetManager.load(Descriptor.COIN_SOUND)
        assetManager.load(Descriptor.LOSE_SOUND)
    }

    override fun render(delta: Float) {
        clearScreen(0f, 0f, 0f)
        update(delta)
        draw()
        if (changeScreen) {
            game.screen = MenuScreen(game) // should always be called when draw finishes
        }
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
    }

    override fun hide() {
        dispose()
    }

    override fun dispose() {
        renderer.dispose()
    }

    private fun update(delta: Float) {
        progress = assetManager.progress
        if (assetManager.update()) {
            waitTime -= delta
            if (waitTime <= 0) {
                changeScreen = true
            }
        }
    }

    private fun draw() {
        viewport.apply()
        renderer.projectionMatrix = camera.combined
        renderer.begin(ShapeRenderer.ShapeType.Filled)
        renderer.rect(
                (GameConfig.HUD_WIDTH - PROGRESS_BAR_WIDTH) / 2f,
                (GameConfig.HUD_HEIGHT - PROGRESS_BAR_HEIGHT) / 2f,
                progress * PROGRESS_BAR_WIDTH,
                PROGRESS_BAR_HEIGHT
        )
        renderer.end()
    }

    companion object {
        private const val PROGRESS_BAR_WIDTH: Float = GameConfig.HUD_WIDTH / 2f
        private const val PROGRESS_BAR_HEIGHT = 60f
    }

}