package com.snakesimple.lwjgl3

import com.badlogic.gdx.tools.texturepacker.TexturePacker

object AssetPacker {

    private const val rawAssetsPath = "lwjgl3/assets-raw/"
    private const val assetsPath = "assets"

    @JvmStatic
    fun main(args: Array<String>) {
        val settings = TexturePacker.Settings()

        TexturePacker.process(settings,
                              "$rawAssetsPath/gameplay",
                              "$assetsPath/gameplay",
                              "gameplay")


        TexturePacker.process(settings,
                              "$rawAssetsPath/ui",
                              "$assetsPath/ui",
                              "ui")

    }
}