package com.jumper.lwjgl3

import com.badlogic.gdx.tools.texturepacker.TexturePacker

object AssetPackerCircleJump {

    private const val source = "lwjgl3/assets-jumper-raw/"
    private const val destination = "assets-jumper"

    @JvmStatic
    fun main(args: Array<String>) {
        val settings = TexturePacker.Settings()
        settings.apply {
            flattenPaths = true
            combineSubdirectories = true
            debug = false
        }

        TexturePacker.process(settings, "$source/gameplay", "$destination/gameplay", "gameplay")


//        TexturePacker.process(settings,
//                              "$source/ui",
//                              "$destination/ui",
//                              "ui")

    }

}