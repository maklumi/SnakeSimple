package com.brickbreaker.screen

import com.badlogic.gdx.ScreenAdapter
import com.brickbreaker.config.GameConfig
import com.util.debug.DebugCameraController
import com.util.game.GameBase

class GameScreen(game: GameBase) : ScreenAdapter() {

    private val controller = GameController()
    private val renderer = GameRenderer()

    override fun show() {
        DebugCameraController.setStartPosition(GameConfig.WORLD_CENTER_X, GameConfig.WORLD_CENTER_Y)
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