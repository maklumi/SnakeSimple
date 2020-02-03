package com.jumper.entity

enum class MonsterState {
    WALKING,
    JUMPING,
    FALLING;

    fun isWalking(): Boolean {
        return this == WALKING
    }

    fun isJumping(): Boolean {
        return this == JUMPING
    }

    fun isFalling(): Boolean {
        return this == FALLING
    }
}