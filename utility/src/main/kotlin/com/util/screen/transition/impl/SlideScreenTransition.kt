package com.util.screen.transition.impl

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Interpolation
import com.util.Direction
import com.util.GdxUtils

import com.util.screen.transition.ScreenTransitionBase


class SlideScreenTransition(
        duration: Float,
        private val slideIn: Boolean = false,
        private val direction: Direction = Direction.LEFT,
        private val interpolation: Interpolation = Interpolation.linear
) : ScreenTransitionBase(duration) {


    override fun render(batch: SpriteBatch, currScreenTexture: Texture, nextScreenTexture: Texture, percentage: Float) {
        var percent = percentage
        percent = interpolation.apply(percent)

        var x = 0f
        var y = 0f

        // drawing order depends on slide type (in or out)
        val bottomTexture = if (slideIn) currScreenTexture else nextScreenTexture
        val topTexture = if (slideIn) nextScreenTexture else currScreenTexture

        val bottomTextureWidth = bottomTexture.width
        val bottomTextureHeight = bottomTexture.height
        val topTextureWidth = topTexture.width
        val topTextureHeight = topTexture.height

        // calculate position offset
        if (direction.isHorizontal) {
            var sign = (if (direction.isLeft) -1 else 1).toFloat() // sign always -1 or 1

            x = sign * topTextureWidth.toFloat() * percent

            if (slideIn) {
                sign = -sign // reverse sign
                x += sign * topTextureWidth
            }
        }

        if (direction.isVertical) {
            var sign = (if (direction.isUp) 1 else -1).toFloat()

            y = sign * topTextureHeight.toFloat() * percent

            if (slideIn) {
                sign = -sign // reverse sign
                y += sign * topTextureHeight
            }
        }

        // drawing
        GdxUtils.clearScreen()
        batch.begin()

        // bottom texture
        batch.draw(bottomTexture,
                   0f, 0f,
                   0f, 0f,
                   bottomTextureWidth.toFloat(), bottomTextureHeight.toFloat(),
                   1f, 1f,
                   0f,
                   0, 0,
                   bottomTextureWidth, bottomTextureHeight,
                   false, true
        )

        // top texture
        batch.draw(topTexture,
                   x, y,
                   0f, 0f,
                   topTextureWidth.toFloat(), topTextureHeight.toFloat(),
                   1f, 1f,
                   0f,
                   0, 0,
                   topTextureWidth, topTextureHeight,
                   false, true
        )

        batch.end()
    }
}
