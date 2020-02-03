package com.jumper.screen

import com.badlogic.gdx.ScreenAdapter
import com.util.game.GameBase

class GameScreen(game: GameBase) : ScreenAdapter() {

    private val renderer = GameRenderer()
    private val controller = GameController()

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