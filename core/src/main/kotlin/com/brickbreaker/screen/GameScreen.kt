package com.brickbreaker.screen

import com.badlogic.gdx.ScreenAdapter
import com.brickbreaker.common.ScoreController
import com.brickbreaker.config.GameConfig
import com.brickbreaker.entity.EntityFactory
import com.util.debug.DebugCameraController
import com.util.game.GameBase

class GameScreen(game: GameBase) : ScreenAdapter() {

    private val scoreController = ScoreController()
    private val factory = EntityFactory()
    private val controller = GameController(factory, scoreController)
    private val renderer = GameRenderer(controller, game.batch, game.assetManager)

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