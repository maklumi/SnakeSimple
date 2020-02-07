package com.platformer.assets

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.TextureAtlas

enum class RegionNames {

    coin,
    coin_spawn,
    decor_box,
    decor_box2,
    ground_left,
    ground_right,
    hazard,
    life,
    platform_center,
    platform_left,
    platform_right,
    platform_single,
    player_falling,
    player_jumping,
    player_spawn;


    private val gamePlayAtlas by lazy { assetManager.get(AssetDescriptors.GAME_PLAY) }

    operator fun invoke(): TextureAtlas.AtlasRegion = gamePlayAtlas.findRegion(this.toString())

    @Suppress("LibGDXStaticResource")
    companion object {
        lateinit var assetManager: AssetManager
    }

    /*
    goto folder with these images, open in terminal
    dir /b *.png > names.txt
    then open names.txt in notepad and replace .png with ,
     */
}