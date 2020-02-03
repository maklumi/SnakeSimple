package com.snakeashley.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.Viewport
import com.snakeashley.Dimension
import com.snakeashley.Position
import com.snakeashley.TextureComponent
import com.snakeashley.common.DIMENSION
import com.snakeashley.common.POSITION
import com.snakeashley.common.TEXTURE
import ktx.ashley.get


class RenderSystem(private val batch: SpriteBatch,
                   private val viewport: Viewport) :
        IteratingSystem(FAMILY) {

    companion object {
        private val FAMILY: Family = Family.all(
                Position::class.java,
                Dimension::class.java,
                TextureComponent::class.java

        ).get()
    }

    override fun update(deltaTime: Float) {
        viewport.apply()
        batch.projectionMatrix = viewport.camera.combined
        batch.begin()

        super.update(deltaTime) // <- calls each entity in family

        batch.end()
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val position = entity[POSITION]!!
        val dimension = entity[DIMENSION]!!
        val texture = entity[TEXTURE]!!

        batch.draw(texture.region,
                   position.x, position.y,
                   dimension.width, dimension.height)
    }

}