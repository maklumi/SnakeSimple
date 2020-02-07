package com.util.shape

import com.badlogic.gdx.math.MathUtils

object ShapeUtils {

    fun createRectangle(width: Float, height: Float): FloatArray {
        return createRectangle(0f, 0f, width, height)
    }

    fun createRectangle(x: Float, y: Float, width: Float, height: Float): FloatArray {
        return floatArrayOf(x, y,       // bottom left corner
                x + width, y,           // bottom right corner
                x + width, y + height,  // top right corner
                x, y + height           // top left corner
        )
    }

    fun createOctagon(radius: Float): FloatArray {
        return createOctagon(0f, 0f, radius)
    }

    fun createOctagon(originX: Float, originY: Float, radius: Float): FloatArray {
        return createPolygon(originX, originY, radius, 8)
    }

    fun createPolygon(originX: Float, originY: Float, radius: Float, vertexCount: Int): FloatArray {
        val ret = FloatArray(vertexCount * 2)

        var i = 0
        var j = 0
        while (i < vertexCount) {
            ret[j++] = originX + radius * MathUtils.cosDeg((360 * i / vertexCount).toFloat()) // x position
            ret[j++] = originY + radius * MathUtils.sinDeg((360 * i / vertexCount).toFloat()) // y position
            i++
        }

        return ret
    }
}
