package com.brickbreaker.assets

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.TextureAtlas

@Suppress("LibGDXStaticResource")
enum class RegionNames {

    background, ball, brick, expand, paddle, shrink, `slow-down`, `speed-up`;

    private val gamePlayAtlas by lazy { assetManager.get(AssetDescriptors.GAME_PLAY) }

    operator fun invoke(): TextureAtlas.AtlasRegion = gamePlayAtlas.findRegion(this.toString())

    companion object {
        lateinit var assetManager: AssetManager
    }

}