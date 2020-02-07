package com.brickbreaker.util

enum class Direction {
    UP, DOWN, LEFT, RIGHT;

    val isUp: Boolean
        get() = this == UP

    val isDown: Boolean
        get() = this == DOWN

    val isLeft: Boolean
        get() = this == LEFT

    val isRight: Boolean
        get() = this == RIGHT

    val isHorizontal: Boolean
        get() = this == LEFT || this == RIGHT

    val isVertical: Boolean
        get() = this == UP || this == DOWN
}
