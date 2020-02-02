package com.snakeashley

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.Logger
import com.badlogic.gdx.utils.Pool
import com.snakesimple.entity.Direction

class Position : Component, Pool.Poolable {
    var x = 0f
    var y = 0f

    override fun reset() {
        x = 0f
        y = 0f
    }
}


class Dimension : Component, Pool.Poolable {
    var width = 1f
    var height = 1f

    override fun reset() {
        width = 1f
        height = 1f
    }
}


class Bounds : Component, Pool.Poolable {
    val rectangle = Rectangle(0f, 0f, 1f, 1f)

    override fun reset() {
        rectangle.setPosition(0f, 0f)
        rectangle.setSize(1f, 1f)
    }
}


class SnakeComponent : Component, Pool.Poolable {
    private val log = Logger(javaClass.simpleName, Logger.DEBUG)

    var head: Entity = Entity()
    val bodyParts = Array<Entity>()

    override fun reset() {
        log.debug("resetting snake component")
        head = Entity()
        bodyParts.clear()
        log.debug("reset done")
    }
}


class DirectionComponent : Component, Pool.Poolable {
    var direction = Direction.RIGHT

    fun isOpposite(other: Direction): Boolean = direction.isOpposite(other)

    override fun reset() {
        direction = Direction.RIGHT
    }
}


class MovementComponent : Component, Pool.Poolable {
    var xSpeed = 0f
    var ySpeed = 0f

    override fun reset() {
        xSpeed = 0f
        ySpeed = 0f
    }
}

class PlayerComponent : Component, Pool.Poolable {
    override fun reset() = Unit
}

class CoinComponent : Component, Pool.Poolable {
    var available: Boolean = false
    override fun reset() {
        available = false
    }
}