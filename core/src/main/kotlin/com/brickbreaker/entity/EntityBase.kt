package com.brickbreaker.entity

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Polygon
import com.badlogic.gdx.math.Vector2
import com.brickbreaker.util.shape.ShapeUtils

abstract class EntityBase {

    var x = 0f
    var y = 0f

    var width = 1f
    var height = 1f

    var bounds: Polygon = Polygon()
        get() {
            field.setPosition(x, y)
            return field
        }

    private val velocity = Vector2()

    fun update(delta: Float) {
        val newX = x + velocity.x * delta
        val newY = y + velocity.y * delta

        setPosition(newX, newY)
    }

    fun setVelocityX(velocityX: Float) {
        velocity.x = velocityX
    }

    fun setVelocity(angleDeg: Float, value: Float) {
        velocity.x = value * MathUtils.cosDeg(angleDeg)
        velocity.y = value * MathUtils.sinDeg(angleDeg)
    }

    fun multiplyVelocityX(xAmount: Float) {
        velocity.x *= xAmount
    }

    fun multiplyVelocityY(yAmount: Float) {
        velocity.y *= yAmount
    }

    fun setPosition(x: Float, y: Float) {
        this.x = x
        this.y = y
    }

    fun setSize(width: Float, height: Float = width) {
        this.width = width
        this.height = height
        bounds.vertices = ShapeUtils.createRectangle(width, height)
    }

}
