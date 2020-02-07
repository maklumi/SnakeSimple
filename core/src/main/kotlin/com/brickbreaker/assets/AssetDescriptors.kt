package com.brickbreaker.assets

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.utils.Array


object AssetDescriptors {

    val FONT = AssetDescriptor(AssetPaths.SCORE_FONT, BitmapFont::class.java)
    val GAME_PLAY = AssetDescriptor(AssetPaths.GAME_PLAY, TextureAtlas::class.java)

    val ALL = Array<AssetDescriptor<*>>().also {
        it.addAll(
                GAME_PLAY,
                FONT
        )
    }

}
