package com.jumper.entity

import com.badlogic.gdx.math.Circle
import com.jumper.config.GameConfig

class Planet {

    var x = 0f
    var y = 0f

    var width = 1f
    var height = 1f

    private val radius = GameConfig.PLANET_HALF_SIZE

    val bounds: Circle
        get() = Circle(x + radius, y + radius, radius)

    fun setSize(width: Float, height: Float) {
        this.width = width
        this.height = height
    }

    fun setPosition(x: Float, y: Float) {
        this.x = x
        this.y = y
    }
}