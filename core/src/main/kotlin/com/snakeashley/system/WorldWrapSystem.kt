package com.snakeashley.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.snakeashley.Position
import com.snakeashley.common.POSITION
import com.snakesimple.config.GameConfig
import ktx.ashley.get

class WorldWrapSystem : IteratingSystem(FAMILY) {

    companion object {
        private val FAMILY = Family.all(
                Position::class.java
        ).get()
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val position = entity[POSITION]!!

        when {
            position.x >= GameConfig.WORLD_WIDTH ->
                position.x = 0f
            position.y >= GameConfig.WORLD_HEIGHT ->
                position.y = 0f
            position.x < 0 ->
                position.x = GameConfig.WORLD_WIDTH - GameConfig.SNAKE_SPEED
            position.y < 0 ->
                position.y = GameConfig.MAX_Y - GameConfig.SNAKE_SPEED
        }
    }
}