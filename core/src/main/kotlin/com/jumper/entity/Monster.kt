package com.jumper.entity

import com.jumper.config.GameConfig
import com.util.entity.EntityBase

class Monster : EntityBase() {
    override fun initSize() {
        setSize(GameConfig.MONSTER_SIZE, GameConfig.MONSTER_SIZE)
    }
}