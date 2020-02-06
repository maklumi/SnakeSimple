package com.jumper.screen

import com.badlogic.gdx.ScreenAdapter
import com.jumper.common.SoundManager
import com.util.game.GameBase

class GameScreen(game: GameBase) : ScreenAdapter() {

    private val soundManager = SoundManager(game.assetManager)
    private val controller = GameController(soundManager)
    private val renderer = GameRenderer(controller, game)

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