package com.jumper.assets

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureAtlas


object AssetDescriptors {

    val FONT = AssetDescriptor<BitmapFont>(AssetPath.FONT, BitmapFont::class.java)

    val GAME_PLAY = AssetDescriptor<TextureAtlas>(AssetPath.GAME_PLAY, TextureAtlas::class.java)

}