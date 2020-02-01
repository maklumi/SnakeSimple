package com.snakesimple.entity

import com.badlogic.gdx.math.Rectangle

abstract class EntityBase {

    var x: Float = 0f
    var y: Float = 0f
    var width: Float = 1f
    var height: Float = 1f

    val bounds: Rectangle
        get() = Rectangle(x, y, width, height)

    protected abstract fun initSize()

    fun setSize(width: Float, height: Float) {
        this.width = width
        this.height = height
    }
}