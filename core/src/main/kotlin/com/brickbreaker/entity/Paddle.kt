package com.brickbreaker.entity

import com.brickbreaker.config.GameConfig
import com.util.entity.EntityBase


class Paddle : EntityBase() {

    var velocityX = 0f

    init {
        initSize()
    }

    override fun initSize() {
        setSize(GameConfig.PADDLE_START_WIDTH, GameConfig.PADDLE_HEIGHT)
        setPosition(GameConfig.PADDLE_START_X, GameConfig.PADDLE_START_Y)
    }

    fun update(delta: Float) {
        var tempX = x
        tempX += velocityX * delta
        // block right
        if (tempX + width >= GameConfig.WORLD_WIDTH) tempX = GameConfig.WORLD_WIDTH - width
        // block left
        if (tempX <= 0f) tempX = 0f
        x = tempX
    }

}