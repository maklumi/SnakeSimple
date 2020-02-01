package com.snakesimple.entity

import com.snakesimple.config.GameConfig

class SnakeHead : EntityBase() {

    var direction = Direction.UP

    fun move() {
        when (direction) {
            Direction.LEFT -> x -= GameConfig.SNAKE_SPEED
            Direction.RIGHT -> x += GameConfig.SNAKE_SPEED
            Direction.UP -> y += GameConfig.SNAKE_SPEED
            Direction.DOWN -> y -= GameConfig.SNAKE_SPEED
        }
    }

    override fun initSize() {
        setSize(GameConfig.SNAKE_SIZE, GameConfig.SNAKE_SIZE)
    }

}