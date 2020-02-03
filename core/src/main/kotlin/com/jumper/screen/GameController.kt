package com.jumper.screen

import com.jumper.config.GameConfig
import com.jumper.entity.Monster
import com.jumper.entity.Planet

class GameController {

    val planet = Planet().apply {
        setSize(GameConfig.PLANET_SIZE, GameConfig.PLANET_SIZE)
        setPosition(GameConfig.WORLD_CENTER_X, GameConfig.WORLD_CENTER_Y)
    }

    val monster = Monster().apply {
        setPosition(GameConfig.WORLD_CENTER_X - GameConfig.MONSTER_HALF_SIZE, GameConfig.WORLD_CENTER_Y + GameConfig.PLANET_HALF_SIZE)
    }

    fun update(delta: Float) {
        monster.update(delta)
    }

}