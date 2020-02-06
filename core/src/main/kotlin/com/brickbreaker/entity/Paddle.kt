package com.brickbreaker.entity

import com.badlogic.gdx.math.MathUtils
import com.brickbreaker.config.GameConfig

class Paddle : EntityBase() {

    fun limitX() {
        x = MathUtils.clamp(x, 0f, GameConfig.WORLD_WIDTH - GameConfig.PADDLE_START_WIDTH)
    }
}