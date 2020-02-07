package com.util.screen.transition.impl

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Interpolation
import com.util.GdxUtils
import com.util.screen.transition.ScreenTransitionBase


class ScaleScreenTransition(
        duration: Float,
        private val scaleOut: Boolean = false,
        private val interpolation: Interpolation = Interpolation.linear
) : ScreenTransitionBase(duration) {


    override fun render(batch: SpriteBatch, currScreenTexture: Texture, nextScreenTexture: Texture, percentage: Float) {
        var percent = percentage
        // interpolate percentage
        percent = interpolation.apply(percent)

        // assume scale out is false (e.g. scaling in)
        var scale = 1 - percent

        if (scaleOut) {
            scale = percent
        }

        // drawing order depends on scale type (in or out)
        val topTexture = if (scaleOut) nextScreenTexture else currScreenTexture
        val bottomTexture = if (scaleOut) currScreenTexture else nextScreenTexture

        val topTextureWidth = topTexture.width
        val topTextureHeight = topTexture.height
        val bottomTextureWidth = bottomTexture.width
        val bottomTextureHeight = bottomTexture.height

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
                   0f, 0f,
                   topTextureWidth / 2f, topTextureHeight / 2f,
                   topTextureWidth.toFloat(), topTextureHeight.toFloat(),
                   scale, scale,
                   0f,
                   0, 0,
                   topTextureWidth, topTextureHeight,
                   false, true
        )

        batch.end()
    }
}
