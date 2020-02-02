package com.snakeashley.common

import com.snakeashley.Bounds
import com.snakeashley.Dimension
import com.snakeashley.Position
import com.snakeashley.SnakeComponent
import ktx.ashley.mapperFor

val POSITION = mapperFor<Position>()
val DIMENSION = mapperFor<Dimension>()
val BOUNDS = mapperFor<Bounds>()

val SNAKE_COMPONENT = mapperFor<SnakeComponent>()