package com.brickbreaker.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.brickbreaker.config.GameConfig

class GameController(private val gameModel: GameModel) {


    fun update(delta: Float) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.F5))
            gameModel.isDrawGrid = !gameModel.isDrawGrid
        if (Gdx.input.isKeyJustPressed(Input.Keys.F6))
            gameModel.isDrawDebug = !gameModel.isDrawDebug
        if (gameModel.isGameOver) return
        val ball = gameModel.ball
        if (Gdx.input.justTouched() && ball.isNotActive) {
            ball.setVelocityY(GameConfig.BALL_START_SPEED)
        }

        gameModel.update(delta)
    }

}