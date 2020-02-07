package com.util

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20


object GdxUtils {

    /**
     * Clears screen using specified [Color].
     *
     * @param color The color for clearing the screen. If null black will be used.
     */
    @JvmOverloads
    fun clearScreen(color: Color = Color.BLACK) {
        Gdx.gl.glClearColor(color.r, color.g, color.b, color.a)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    }

}

