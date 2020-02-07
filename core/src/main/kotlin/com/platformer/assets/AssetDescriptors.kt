package com.platformer.assets

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.utils.Array


object AssetDescriptors {

    val LEVEL_01 = AssetDescriptor(AssetPaths.LEVEL_01, TiledMap::class.java)

    val GAME_PLAY = AssetDescriptor(AssetPaths.GAME_PLAY, TextureAtlas::class.java)

    val ALL = Array<AssetDescriptor<*>>().also {
        it.addAll(
                GAME_PLAY

        )
    }
}