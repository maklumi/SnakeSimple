package com.snakeashley.system.debug

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.viewport.Viewport
import com.snakeashley.Bounds
import com.snakeashley.common.DIMENSION
import com.snakeashley.common.POSITION
import ktx.ashley.allOf
import ktx.ashley.get

class DebugRenderSystem(
        private val renderer: ShapeRenderer,
        private val viewport: Viewport
) : IteratingSystem(allOf(Bounds::class).get()) {

    override fun update(deltaTime: Float) {
        val oldColor = renderer.color.cpy()
        viewport.apply()
        renderer.projectionMatrix = viewport.camera.combined
        renderer.begin(ShapeRenderer.ShapeType.Line)
        renderer.color = Color.ROYAL

        super.update(deltaTime) // processEntity

        renderer.end()
        renderer.color = oldColor
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val pos = entity[POSITION]!!
        val dim = entity[DIMENSION]!!
        renderer.rect(pos.x, pos.y, dim.width, dim.height)
    }
}