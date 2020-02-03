package com.jumper.entity

import com.badlogic.gdx.math.MathUtils
import com.jumper.config.GameConfig
import com.util.entity.EntityBase

class Monster : EntityBase() {

    var angleDeg = GameConfig.START_ANGLE
    private val angleDegSpeed = GameConfig.MONSTER_START_ANG_SPEED
    private val originX = GameConfig.WORLD_CENTER_X
    private val originY = GameConfig.WORLD_CENTER_Y

    private var speed = 0f
    private var acceleration = GameConfig.MONSTER_START_ACC
    private var state = MonsterState.WALKING
    val isWalking: Boolean
        get() = state.isWalking()

    override fun initSize() {
        setSize(GameConfig.MONSTER_SIZE, GameConfig.MONSTER_SIZE)
    }

    fun update(delta: Float) {
        if (state.isJumping()) {
            speed += acceleration * delta

            // when reached max speed switch state to falling
            if (speed >= GameConfig.MONSTER_MAX_SPEED) {
                fall()
            }
        } else if (state.isFalling()) {
            // falling down
            speed -= acceleration * delta

            // when speed is 0 we are walking again
            if (speed <= 0) {
                speed = 0f
                walk()
            }
        }

        angleDeg += angleDegSpeed * delta
        angleDeg %= 360

        val radius = GameConfig.PLANET_HALF_SIZE + speed
        val newX = originX + MathUtils.cosDeg(-angleDeg) * radius
        val newY = originY + MathUtils.sinDeg(-angleDeg) * radius
        setPosition(newX, newY)
    }

    fun jump() {
        state = MonsterState.JUMPING
    }

    private fun fall() {
        state = MonsterState.FALLING
    }

    private fun walk() {
        state = MonsterState.WALKING
    }
}