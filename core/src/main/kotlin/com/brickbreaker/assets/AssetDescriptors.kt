package com.brickbreaker.assets

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.utils.Array


object AssetDescriptors {

    private val FONT = AssetDescriptor(AssetPaths.SCORE_FONT, BitmapFont::class.java)

    val ALL = Array<AssetDescriptor<*>>().also {
        it.addAll(
                FONT
        )
    }

}
