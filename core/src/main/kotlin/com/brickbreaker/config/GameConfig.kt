package com.brickbreaker.config

object GameConfig {
    const val WIDTH = 1024 // pixels
    const val HEIGHT = 768 // pixels
    const val HUD_WIDTH = 1024f // world units
    const val HUD_HEIGHT = 768f // world units
    const val WORLD_WIDTH = 32f // world units
    const val WORLD_HEIGHT = 24f // world units
    const val WORLD_CENTER_X = WORLD_WIDTH / 2f // world units
    const val WORLD_CENTER_Y = WORLD_HEIGHT / 2f // world units

    const val PADDLE_START_WIDTH = 3f // world units
    const val PADDLE_HEIGHT = 1f // world units
    const val PADDLE_START_X = (WORLD_WIDTH - PADDLE_START_WIDTH) / 2f // world units
    const val PADDLE_START_Y = 1f // world units
    const val PADDLE_VELOCITY_X = 15f // world units

    const val BRICK_WIDTH = 2.125f // world units
    const val BRICK_HEIGHT = 1f // world units
    const val LEFT_PAD = 0.5f // world units
    const val TOP_PAD = 2.5f // world units
    const val COLUMN_SPACING = 0.5f // world units
    const val COLUMN_COUNT = 12 // count
    const val ROW_SPACING = 0.5f // world units
    const val ROW_COUNT = 6 // count

    const val BALL_SIZE = 0.8f // world units
    const val BALL_HALF_SIZE = BALL_SIZE / 2f
    const val BALL_START_X = PADDLE_START_X + (PADDLE_START_WIDTH - BALL_SIZE) / 2f
    const val BALL_START_Y = PADDLE_START_Y + PADDLE_HEIGHT
    const val BALL_START_SPEED = 16f
    const val BALL_START_ANGLE = 60f // degrees

    const val BRICK_SCORE = 10 // score

}