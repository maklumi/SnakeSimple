package com.snakesimple.assets

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.scenes.scene2d.ui.Skin


object Descriptor {

    val UI_FONT = AssetDescriptor<BitmapFont>(Path.UI_FONT, BitmapFont::class.java)

    val GAME_PLAY = AssetDescriptor<TextureAtlas>(Path.GAME_PLAY, TextureAtlas::class.java)

    val UI_SKIN = AssetDescriptor<Skin>(Path.UI_SKIN, Skin::class.java)

}