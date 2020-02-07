package com.brickbreaker.screen

import com.badlogic.gdx.ScreenAdapter
import com.brickbreaker.common.ScoreController
import com.brickbreaker.common.SoundController
import com.brickbreaker.config.GameConfig
import com.brickbreaker.entity.EntityFactory
import com.brickbreaker.input.PaddleInputController
import com.util.debug.DebugCameraController
import com.util.game.GameBase

class GameScreen(game: GameBase) : ScreenAdapter() {

    private val scoreController = ScoreController()
    private val soundController = SoundController(game.assetManager)
    private val factory = EntityFactory(game.assetManager)
    private val gameModel = GameModel(factory, scoreController, soundController)
    private val controller = GameController(gameModel)
    private val gameView = GameView(gameModel, game.batch, game.assetManager)
    private val paddleInputController = PaddleInputController(gameModel.paddle, gameView)

    override fun show() {
        DebugCameraController.setStartPosition(GameConfig.WORLD_CENTER_X, GameConfig.WORLD_CENTER_Y)
    }

    override fun render(delta: Float) {
        paddleInputController.update(delta)
        controller.update(delta)
        gameView.render(delta)
    }

    override fun resize(width: Int, height: Int) {
        gameView.resize(width, height)
    }

    override fun hide() {
        dispose()
    }

    override fun dispose() {
        gameView.dispose()
    }

}