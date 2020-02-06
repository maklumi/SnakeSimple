package com.brickbreaker.input

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.brickbreaker.config.GameConfig
import com.brickbreaker.entity.Paddle


class PaddleInputController(private val paddle: Paddle) {

    fun update(delta: Float) {
        var velocityX = 0f

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            velocityX = -GameConfig.PADDLE_VELOCITY_X
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            velocityX = GameConfig.PADDLE_VELOCITY_X
        }

        paddle.velocityX = velocityX
        paddle.update(delta)
    }

}