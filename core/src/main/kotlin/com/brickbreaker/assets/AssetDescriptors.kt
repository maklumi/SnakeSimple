package com.brickbreaker.assets

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.ParticleEffect
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.utils.Array


object AssetDescriptors {

    val FONT = AssetDescriptor(AssetPaths.SCORE_FONT, BitmapFont::class.java)
    val GAME_PLAY = AssetDescriptor(AssetPaths.GAME_PLAY, TextureAtlas::class.java)
    val FIRE = AssetDescriptor(AssetPaths.FIRE, ParticleEffect::class.java)

    val ALL = Array<AssetDescriptor<*>>().also {
        it.addAll(
                FIRE,
                GAME_PLAY,
                FONT
        )
    }

}
