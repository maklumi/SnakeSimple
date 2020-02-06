package com.brickbreaker.entity

import com.brickbreaker.config.GameConfig

class EntityFactory {

    fun createPaddle(): Paddle {
        val paddle = Paddle()
        paddle.setPosition(GameConfig.PADDLE_START_X, GameConfig.PADDLE_START_Y)
        paddle.setSize(GameConfig.PADDLE_START_WIDTH, GameConfig.PADDLE_HEIGHT)
        return paddle
    }

}