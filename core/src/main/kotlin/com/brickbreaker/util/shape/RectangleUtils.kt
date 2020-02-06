package com.brickbreaker.util.shape

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2


object RectangleUtils {

    fun getBottomLeft(rectangle: Rectangle): Vector2 {
        return Vector2(rectangle.x, rectangle.y)
    }

    fun getBottomRight(rectangle: Rectangle): Vector2 {
        return Vector2(rectangle.x + rectangle.width, rectangle.y)
    }

    fun getTopLeft(rectangle: Rectangle): Vector2 {
        return Vector2(rectangle.x, rectangle.y + rectangle.height)
    }

    fun getTopRight(rectangle: Rectangle): Vector2 {
        return Vector2(rectangle.x + rectangle.width, rectangle.y + rectangle.height)
    }

}
