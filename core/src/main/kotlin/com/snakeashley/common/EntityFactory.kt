package com.snakeashley.common

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.PooledEngine
import com.snakeashley.Bounds
import com.snakeashley.Dimension
import com.snakeashley.Position
import com.snakeashley.SnakeComponent
import com.snakesimple.config.GameConfig
import ktx.ashley.entity


class EntityFactory(private val engine: PooledEngine) {

    // linking snakeHead entity and body part entity
    // be careful to dispose them separately
    fun createSnake(): Entity {
        return engine.entity {
            with<SnakeComponent> {
                head = createSnakeHead()
            }
        }
    }

    private fun createSnakeHead(): Entity {
        return engine.entity {
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