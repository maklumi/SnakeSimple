package com.snakesimple.config

object GameConfig {

    const val WIDTH = 800f // pixels
    const val HEIGHT = 480f // pixels

    const val WORLD_WIDTH = 25f // world units
    const val WORLD_HEIGHT = 15f // world units

    const val WORLD_CENTER_X = WORLD_WIDTH / 2f // world units
    const val WORLD_CENTER_Y = WORLD_HEIGHT / 2f // world units

    const val SNAKE_SIZE = 1f // world units
    const val MOVE_TIME = 0.2f
    const val SNAKE_SPEED = 1f // world units

    const val COIN_SIZE = 1f // world units
    const val COIN_SCORE = 20

    const val HUD_WIDTH = 800f // world units
    const val HUD_HEIGHT = 480f // world units

    private const val Y_OFFSET = 2f // space for ui score text
    const val MAX_Y = WORLD_HEIGHT - Y_OFFSET
}