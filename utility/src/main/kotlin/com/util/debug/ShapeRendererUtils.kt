package com.util.debug

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Polygon
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.utils.Array
import com.util.entity.EntityBase


object ShapeRendererUtils {

    fun rect(renderer: ShapeRenderer, rectangle: Rectangle) {
        renderer.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height)
    }

    fun circle(renderer: ShapeRenderer, circle: Circle) {
        renderer.circle(circle.x, circle.y, circle.radius, 15)
    }

    fun polygon(renderer: ShapeRenderer, polygon: Polygon) {
        renderer.polygon(polygon.transformedVertices)
    }

    fun <T : EntityBase> entities(renderer: ShapeRenderer, entities: Array<T>) {
        val iterator = Array.ArrayIterator<T>(entities)
        while (iterator.hasNext()) {
            val entityBase = iterator.next()
            polygon(renderer, entityBase.bounds)
        }
    }
}
