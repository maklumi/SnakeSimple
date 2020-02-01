package com.snakesimple.entity

import com.snakesimple.config.GameConfig

class BodyPart : EntityBase() {

    var justAdded = true

    override fun initSize() {
        setSize(GameConfig.SNAKE_SIZE, GameConfig.SNAKE_SIZE)
    }
}