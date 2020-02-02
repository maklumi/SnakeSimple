package com.snakeashley.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.snakeashley.DirectionComponent
import com.snakeashley.PlayerComponent
import com.snakeashley.common.DIRECTION
import com.snakesimple.entity.Direction
import ktx.ashley.get

class PlayerControlSystem : IteratingSystem(FAMILY) {

    companion object {
        private val FAMILY: Family = Family.all(
                DirectionComponent::class.java,
                PlayerComponent::class.java
        ).get()
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val left = Gdx.input.isKeyPressed(Input.Keys.LEFT)
        val right = Gdx.input.isKeyPressed(Input.Keys.RIGHT)
        val up = Gdx.input.isKeyPressed(Input.Keys.UP)
        val down = Gdx.input.isKeyPressed(Input.Keys.DOWN)

        val directionComponent = entity[DIRECTION]!!

        fun updateDirection(direction: Direction) {
            if (!directionComponent.isOpposite(direction)) {
                directionComponent.direction = direction
            }
        }

        when {
            left -> updateDirection(Direction.LEFT)
            right -> updateDirection(Direction.RIGHT)
            up -> updateDirection(Direction.UP)
            down -> updateDirection(Direction.DOWN)
        }
    }

}