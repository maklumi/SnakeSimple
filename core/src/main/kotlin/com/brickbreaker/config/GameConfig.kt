package com.brickbreaker.config

object GameConfig {
    const val WIDTH = 1024 // pixels
    const val HEIGHT = 768 // pixels
    const val WORLD_WIDTH = 32f // world units
    const val WORLD_HEIGHT = 24f // world units
    const val WORLD_CENTER_X = WORLD_WIDTH / 2f // world units
    const val WORLD_CENTER_Y = WORLD_HEIGHT / 2f // world units

    const val PADDLE_START_WIDTH = 3f // world units
    const val PADDLE_HEIGHT = 1f // world units
    const val PADDLE_START_X = (WORLD_WIDTH - PADDLE_START_WIDTH) / 2f // world units
    const val PADDLE_START_Y = 1f // world units
    const val PADDLE_VELOCITY_X = 15f // world units
}