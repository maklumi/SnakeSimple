package com.snakesimple.entity

import com.snakesimple.config.GameConfig
import com.util.entity.EntityBase

class SnakeHead : EntityBase() {

    override fun initSize() {
        setSize(GameConfig.SNAKE_SIZE, GameConfig.SNAKE_SIZE)
    }
}