package com.snakeashley.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.snakeashley.DirectionComponent
import com.snakeashley.MovementComponent
import com.snakeashley.common.DIRECTION
import com.snakeashley.common.MOVEMENT
import com.snakesimple.config.GameConfig
import com.snakesimple.entity.Direction
import ktx.ashley.get

class DirectionSystem : IteratingSystem(FAMILY) {

    companion object {
        private val FAMILY: Family = Family.all(
                DirectionComponent::class.java,
                MovementComponent::class.java
        ).get()
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val dirCom = entity[DIRECTION]!!
        val moveCom = entity[MOVEMENT]!!

        // reset speed to 0 by default
        moveCom.xSpeed = 0f
        moveCom.ySpeed = 0f

        // set speed based on direction
        when (dirCom.direction) {
            Direction.LEFT -> moveCom.xSpeed = -GameConfig.SNAKE_SPEED
            Direction.RIGHT -> moveCom.xSpeed = GameConfig.SNAKE_SPEED
            Direction.UP -> moveCom.ySpeed = GameConfig.SNAKE_SPEED
            Direction.DOWN -> moveCom.ySpeed = -GameConfig.SNAKE_SPEED
        }
    }
}