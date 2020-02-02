package com.snakeashley.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IntervalIteratingSystem
import com.snakeashley.SnakeComponent
import com.snakeashley.common.MOVEMENT
import com.snakeashley.common.POSITION
import com.snakeashley.common.SNAKE_COMPONENT
import com.snakesimple.config.GameConfig
import ktx.ashley.get
import com.badlogic.gdx.utils.Array

class SnakeMovementSystem : IntervalIteratingSystem(FAMILY, GameConfig.MOVE_TIME) {

    companion object {
        private val FAMILY = Family.all(SnakeComponent::class.java).get()
    }

    private var prevX = 0f
    private var prevY = 0f

    override fun processEntity(entity: Entity) {
        val snakeComponent = entity[SNAKE_COMPONENT]!!
        moveHead(snakeComponent.head)
        moveBodyParts(snakeComponent.bodyParts)
    }

    private fun moveHead(head: Entity) {
        val movementComponent = head[MOVEMENT]!!
        val positionComponent = head[POSITION]!!

        prevX = positionComponent.x
        prevY = positionComponent.y

        positionComponent.x += movementComponent.xSpeed
        positionComponent.y += movementComponent.ySpeed
    }

    private fun moveBodyParts(bodyParts: Array<Entity>) {
        if (!bodyParts.isEmpty) {
            val tail = bodyParts.removeIndex(0)
            val positionComponent = tail[POSITION]!!
            positionComponent.x = prevX
            positionComponent.y = prevY
            bodyParts.add(tail)
        }
    }
}