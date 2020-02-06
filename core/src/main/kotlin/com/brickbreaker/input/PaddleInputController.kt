package com.brickbreaker.input

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.brickbreaker.config.GameConfig
import com.brickbreaker.entity.Paddle


class PaddleInputController(private val paddle: Paddle) {

    fun update(delta: Float) {
        var velX = 0f

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            velX = -GameConfig.PADDLE_VELOCITY_X
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            velX = GameConfig.PADDLE_VELOCITY_X
        }

        paddle.setVelocityX(velX)
        paddle.update(delta)
    }

}