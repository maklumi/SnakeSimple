package com.util.screen.transition.impl

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Interpolation
import com.util.GdxUtils
import com.util.screen.transition.ScreenTransitionBase


class FadeScreenTransition(duration: Float) : ScreenTransitionBase(duration) {

    override fun render(batch: SpriteBatch, currScreenTexture: Texture, nextScreenTexture: Texture, percentage: Float) {
        var percent = percentage
        val currentScreenWidth = currScreenTexture.width
        val currentScreenHeight = currScreenTexture.height
        val nextScreenWidth = nextScreenTexture.width
        val nextScreenHeight = nextScreenTexture.height

        // interpolate percentage
        percent = Interpolation.fade.apply(percent)

        // clear screen
        GdxUtils.clearScreen()

        val oldColor = batch.color.cpy()

        batch.begin()

        // draw current screen
        batch.setColor(1f, 1f, 1f, 1f - percent) // white color with transparency
        batch.draw(currScreenTexture, // texture
                   0f, 0f, // x,y
                   0f, 0f, // origin x,y
                   currentScreenWidth.toFloat(), currentScreenHeight.toFloat(), // width,height
                   1f, 1f, // scale x,y
                   0f, // rotation
                   0, 0, // src x,y
                   currentScreenWidth, currentScreenHeight, // src width, height
                   false, true // flip x,y
        )

        // draw next screen
        batch.setColor(1f, 1f, 1f, percent) // white color with transparency
        batch.draw(nextScreenTexture,
                0f, 0f,
                0f, 0f,
                nextScreenWidth.toFloat(), nextScreenHeight.toFloat(),
                1f, 1f,
                0f,
                0, 0,
                nextScreenWidth, nextScreenHeight,
                false, true
        )

        // reset old color back
        batch.color = oldColor

        batch.end()
    }
}
