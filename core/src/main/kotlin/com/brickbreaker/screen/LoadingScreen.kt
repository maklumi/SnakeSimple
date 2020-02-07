package com.brickbreaker.screen

import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.Logger
import com.badlogic.gdx.utils.viewport.FitViewport
import com.brickbreaker.assets.AssetDescriptors
import com.brickbreaker.assets.RegionNames
import com.brickbreaker.config.GameConfig
import com.util.game.GameBase
import ktx.app.clearScreen

class LoadingScreen(private val game: GameBase) : ScreenAdapter() {

    private val assetManager = game.assetManager
    private val camera = OrthographicCamera()
    private val viewport = FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT, camera)
    private val renderer = ShapeRenderer()
    private var progress = 0f
    private var waitTime = 0.75f
    private var changeScreen = false

    companion object {
        private const val BAR_WIDTH = GameConfig.HUD_WIDTH / 2f
        private const val BAR_HEIGHT = 60f
    }

    override fun show() {
        assetManager.logger = Logger("LoadingScreen", Logger.DEBUG)
        AssetDescriptors.ALL.forEach { assetManager.load(it) }
        RegionNames.assetManager = assetManager
    }

    override fun render(delta: Float) {
        clearScreen(0f, 0f, 0f)
        update(delta)
        draw()

        if (changeScreen)
            game.screen = GameScreen(game)
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
            if (waitTime <= 0) changeScreen = true
        }
    }

    private fun draw() {
        viewport.apply()
        renderer.projectionMatrix = camera.combined
        renderer.begin(ShapeRenderer.ShapeType.Filled)
        renderer.rect(
                (GameConfig.HUD_WIDTH - BAR_WIDTH) / 2f, (GameConfig.HUD_HEIGHT - BAR_HEIGHT) / 2f,
                progress * BAR_WIDTH, BAR_HEIGHT
        )
        renderer.end()
    }

}