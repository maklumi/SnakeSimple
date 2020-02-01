package com.snakesimple.screen

import com.badlogic.gdx.ScreenAdapter
import com.snakesimple.SimpleSnakeMain

class GameScreen(game: SimpleSnakeMain) : ScreenAdapter() {

    private val assetManager = game.assetManager

    private val controller = GameController()
    private val renderer = GameRenderer(game.batch, controller, assetManager)

    override fun show() {

    }

    override fun render(delta: Float) {
        controller.update(delta)
        renderer.render(delta)
    }

    override fun resize(width: Int, height: Int) {
        renderer.resize(width, height)
    }

    override fun hide() {
        dispose()
    }

    override fun dispose() {
        renderer.dispose()
    }
}