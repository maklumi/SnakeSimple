package com.snakeashley.common

import com.badlogic.ashley.core.PooledEngine
import com.snakeashley.Bounds
import com.snakeashley.Dimension
import com.snakeashley.Position
import com.snakesimple.config.GameConfig
import ktx.ashley.entity


class EntityFactory(private val engine: PooledEngine) {

    fun createSnakeHead() {
        engine.entity {
            val position = with<Position>()
            val dimension = with<Dimension> {
                width = GameConfig.SNAKE_SIZE
                height = GameConfig.SNAKE_SIZE
            }
            with<Bounds> {
                rectangle.set(position.x, position.y, dimension.width, dimension.height)
            }
        }
    }
}