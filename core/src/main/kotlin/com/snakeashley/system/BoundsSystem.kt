package com.snakeashley.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.snakeashley.Bounds
import com.snakeashley.Dimension
import com.snakeashley.Position
import com.snakeashley.common.BOUNDS
import com.snakeashley.common.DIMENSION
import com.snakeashley.common.POSITION
import ktx.ashley.get

class BoundsSystem : IteratingSystem(FAMILY) {

    companion object {
        private val FAMILY: Family = Family.all(
                Bounds::class.java,
                Dimension::class.java,
                Position::class.java
        ).get()
    }

    /*
    The entity will have all the component stated above.
    Asking for component outside the family may result in calling null.
     */
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val bounds = entity[BOUNDS]!!
        val dimension = entity[DIMENSION]!!
        val position = entity[POSITION]!!

        bounds.rectangle.setPosition(position.x, position.y)
        bounds.rectangle.setSize(dimension.width, dimension.height)
    }
}