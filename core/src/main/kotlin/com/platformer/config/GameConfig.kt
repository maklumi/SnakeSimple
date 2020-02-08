package com.platformer.config

object GameConfig {

    // desktop only
    const val WIDTH = 1280 // pixels
    const val HEIGHT = 780 // pixels

    const val WORLD_WIDTH = 20f // world units
    const val WORLD_HEIGHT = 12f // world units

    const val WORLD_CENTER_X = WORLD_WIDTH / 2f
    const val WORLD_CENTER_Y = WORLD_HEIGHT / 2f

    const val HUD_WIDTH = 1280f // world unit, 1:1 pixel per unit
    const val HUD_HEIGHT = 780f

    const val UNIT_SCALE = 1f / 64f // 64 pixels tile width in tmx map is 1 world unit

    const val PLAYER_SIZE = 0.95f
    const val COIN_SIZE = 0.5f

    const val MOVE_VELOCITY = 3f
    const val JUMP_VELOCITY = 9f
    const val GRAVITY_Y = -14f

    const val LIVES_START = 3
    const val LIFE_WIDTH = 40f
    const val LIFE_HEIGHT = 40f
    const val LIFE_SPACING = 10f

    const val COIN_SCORE = 20

}

