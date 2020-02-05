package com.jumper.config

object GameConfig {

    const val WIDTH = 600f // pixels
    const val HEIGHT = 800f // pixels

    const val HUD_WIDTH = 600f // world units
    const val HUD_HEIGHT = 800f // world units

    const val WORLD_WIDTH = 16f // world units
    const val WORLD_HEIGHT = 24f // world units

    const val WORLD_CENTER_X = WORLD_WIDTH / 2f // world units
    const val WORLD_CENTER_Y = WORLD_HEIGHT / 2f // world units

    const val PLANET_SIZE = 9f // world units
    const val PLANET_HALF_SIZE = PLANET_SIZE / 2f // world units

    const val MONSTER_SIZE = 1f // world units
    const val MONSTER_HALF_SIZE = MONSTER_SIZE / 2f // world units
    const val MONSTER_START_ANG_SPEED = 45f
    const val START_ANGLE = -90f

    const val MONSTER_MAX_SPEED = 2f
    const val MONSTER_START_ACC = 4f

    const val COIN_SIZE = 1f // world units
    const val COIN_SPAWN_TIME = 1.25f
    const val MAX_COINS = 4

    const val OBSTACLE_SIZE = 1f // world units
    const val OBSTACLE_SPAWN_TIME = 0.75f
    const val MAX_OBSTACLES = 4

}