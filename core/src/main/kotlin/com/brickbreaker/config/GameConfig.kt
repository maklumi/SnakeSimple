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
    const val PADDLE_RESIZE_FACTOR = 0.15f // percentage
    const val PADDLE_MAX_WIDTH = 16f
    const val PADDLE_EXPAND_SHRINK_SPEED = 6f
    const val PADDLE_MIN_WIDTH = 1.2f

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
    const val BALL_SPEED_FACTOR = 0.15f // 15%
    const val BALL_MIN_SPEED = 10f
    const val BALL_MAX_SPEED = 22f

    const val BRICK_SCORE = 10 // score

    const val PICKUP_SIZE = 1f // world units
    const val PICKUP_VELOCITY_Y = -6f // gravity

    const val LIVES_START = 3

    const val LIFE_HUD_WIDTH = 40f // world units (hud)
    const val LIFE_HUD_HEIGHT = 12f // world units (hud)
    const val LIFE_HUD_SPACING = 10f // world units (hud)

}