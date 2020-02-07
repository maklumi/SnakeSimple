package com.brickbreaker.entity

enum class PickupType {
    EXPAND,
    SHRINK,
    SLOW_DOWN,
    SPEED_UP;

    val isExpand: Boolean
        get() = this == EXPAND

    val isShrink: Boolean
        get() = this == SHRINK

    val isSlowDown: Boolean
        get() = this == SLOW_DOWN

    val isSpeedUp: Boolean
        get() = this == SPEED_UP

    companion object {
        fun random(): PickupType = values().random()
    }

}
