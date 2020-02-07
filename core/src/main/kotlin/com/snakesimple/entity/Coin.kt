package com.snakesimple.entity

import com.snakesimple.config.GameConfig
import com.util.entity.EntityBase

class Coin : EntityBase() {

    var available = false

    fun initSize() {
        setSize(GameConfig.COIN_SIZE, GameConfig.COIN_SIZE)
    }

}