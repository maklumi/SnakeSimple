package com.snakesimple.entity

import com.snakesimple.config.GameConfig

class Coin : EntityBase() {

    var available = false

    override fun initSize() {
        setSize(GameConfig.COIN_SIZE, GameConfig.COIN_SIZE)
    }

    fun setPosition(x: Float, y: Float) {
        this.x = x
        this.y = y
    }
}