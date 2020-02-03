package com.jumper.entity

import com.badlogic.gdx.math.MathUtils
import com.jumper.config.GameConfig
import com.util.entity.EntityBase

class Monster : EntityBase() {

    var angleDeg = GameConfig.START_ANGLE
    private val angleDegSpeed = GameConfig.MONSTER_START_ANG_SPEED
    private val radius = GameConfig.PLANET_HALF_SIZE
    private val originX = GameConfig.WORLD_CENTER_X
    private val originY = GameConfig.WORLD_CENTER_Y

    override fun initSize() {
        setSize(GameConfig.MONSTER_SIZE, GameConfig.MONSTER_SIZE)
    }

    fun update(delta: Float) {
        angleDeg += angleDegSpeed * delta
        angleDeg %= 360

        val newX = originX + MathUtils.cosDeg(-angleDeg) * radius
        val newY = originY + MathUtils.sinDeg(-angleDeg) * radius
        setPosition(newX, newY)
    }
}