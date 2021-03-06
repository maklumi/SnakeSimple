package com.util.entity

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Polygon
import com.badlogic.gdx.math.Vector2
import com.util.entity.script.EntityScript
import com.util.entity.script.ScriptController
import com.util.shape.ShapeUtils

abstract class EntityBase {

    var x = 0f
    var y = 0f

    var width = 1f
        set(value) {
            field = value
            bounds.vertices = createVertices()
        }

    var height = 1f

    var bounds: Polygon = Polygon()
        get() {
            field.setPosition(x, y)
            return field
        }

    open val speed: Float
        get() = velocity.len()

    val velocity = Vector2()

    val isNotActive: Boolean
        get() = velocity.isZero

    open val angleDeg: Float
        get() = MathUtils.atan2(velocity.y, velocity.x) * MathUtils.radiansToDegrees

    open val scriptController: ScriptController? = null

    fun addScript(entityScript: EntityScript<out EntityBase>) {
        if (scriptController == null) println("EntityBase: scriptController is null")
        scriptController?.addScript(entityScript)
    }

    open fun update(delta: Float) {
        val newX = x + velocity.x * delta
        val newY = y + velocity.y * delta

        setPosition(newX, newY)
        scriptController?.update(delta)
    }

    open fun createVertices(): FloatArray {
        return ShapeUtils.createRectangle(width, height)
    }

    fun setVelocityX(velocityX: Float) {
        velocity.x = velocityX
    }

    fun setVelocityY(velocityY: Float) {
        velocity.y = velocityY
    }

    fun setVelocity(angleDeg: Float, speed: Float) {
        velocity.x = speed * MathUtils.cosDeg(angleDeg)
        velocity.y = speed * MathUtils.sinDeg(angleDeg)
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
        bounds.vertices = createVertices()
    }

    fun stop() {
        velocity.setZero()
    }
}
