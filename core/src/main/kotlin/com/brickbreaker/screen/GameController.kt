package com.brickbreaker.screen

import com.brickbreaker.entity.EntityFactory
import com.brickbreaker.input.PaddleInputController

class GameController(factory: EntityFactory) {

    val paddle = factory.createPaddle()
    private val paddleInputController = PaddleInputController(paddle)

    fun update(delta: Float) {
        paddleInputController.update(delta)
        paddle.limitX()
    }
}