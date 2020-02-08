package com.platformer.entity

enum class PlayerState {
    JUMPING,
    FALLING,
    DEAD;

    val isJumping
        get() = this == JUMPING

    val isFalling
        get() = this == FALLING

    val isDead
        get() = this == DEAD
}
