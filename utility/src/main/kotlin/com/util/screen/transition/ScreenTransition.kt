package com.util.screen.transition

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch


interface ScreenTransition {

    val duration: Float

    fun render(batch: SpriteBatch,
               currScreenTexture: Texture,
               nextScreenTexture: Texture,
               percent: Float)
}
