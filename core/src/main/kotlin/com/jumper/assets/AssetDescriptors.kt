package com.jumper.assets

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.scenes.scene2d.ui.Skin


object AssetDescriptors {

    val FONT = AssetDescriptor<BitmapFont>(AssetPath.FONT, BitmapFont::class.java)

    val GAME_PLAY = AssetDescriptor<TextureAtlas>(AssetPath.GAME_PLAY, TextureAtlas::class.java)

    val SKIN = AssetDescriptor<Skin>(AssetPath.SKIN, Skin::class.java)

    val COIN = AssetDescriptor<Sound>(AssetPath.COIN, Sound::class.java)
    val JUMP = AssetDescriptor<Sound>(AssetPath.JUMP, Sound::class.java)
    val LOSE = AssetDescriptor<Sound>(AssetPath.LOSE, Sound::class.java)
}