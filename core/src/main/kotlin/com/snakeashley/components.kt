package com.snakeashley

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.utils.Pool

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