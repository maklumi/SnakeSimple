package com.snakeashley.system

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.Viewport
import com.snakesimple.common.GameManager


class HudRenderSystem(
        private val batch: SpriteBatch,
        private val hudViewport: Viewport,
        private val font: BitmapFont
) : EntitySystem() {

    private val padding = 20.0f
    private val layout = GlyphLayout()

    override fun update(deltaTime: Float) {
        GameManager.displayScores(deltaTime)
        hudViewport.apply()
        batch.projectionMatrix = hudViewport.camera.combined
        batch.begin()

        val y = hudViewport.worldHeight - padding
        layout.setText(font, "HIGH SCORE: ${GameManager.displayHighScore}")
        font.draw(batch, layout, padding, y)

        val x = hudViewport.worldWidth - layout.width
        layout.setText(font, "SCORE: ${GameManager.displayScore}")
        font.draw(batch, layout, x, y)

        batch.end()
    }
}