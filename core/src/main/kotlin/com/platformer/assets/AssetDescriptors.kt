package com.platformer.assets

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.utils.Array


object AssetDescriptors {

    private val MAP_PARAMS = TmxMapLoader.Parameters().apply {
        convertObjectToTileSpace = true
    }

    val LEVEL_01 = AssetDescriptor(AssetPaths.LEVEL_01, TiledMap::class.java, MAP_PARAMS)
    val SKIN = AssetDescriptor(AssetPaths.SKIN, Skin::class.java)
    val GAME_PLAY = AssetDescriptor(AssetPaths.GAME_PLAY, TextureAtlas::class.java)
    val FONT = AssetDescriptor(AssetPaths.FONT, BitmapFont::class.java)
    val BACKGROUND = AssetDescriptor(AssetPaths.BACKGROUND, Texture::class.java)

    val ALL = Array<AssetDescriptor<*>>().also {
        it.addAll(
                LEVEL_01,
                SKIN,
                GAME_PLAY,
                FONT,
                BACKGROUND

        )
    }
}