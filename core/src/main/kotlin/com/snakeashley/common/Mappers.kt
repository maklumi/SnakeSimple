package com.snakeashley.common

import com.snakeashley.*
import ktx.ashley.mapperFor

val POSITION = mapperFor<Position>()
val DIMENSION = mapperFor<Dimension>()
val BOUNDS = mapperFor<Bounds>()

val SNAKE_COMPONENT = mapperFor<SnakeComponent>()

val DIRECTION = mapperFor<DirectionComponent>()
val MOVEMENT = mapperFor<MovementComponent>()

val COIN = mapperFor<CoinComponent>()