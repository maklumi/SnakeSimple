package com.platformer

import com.badlogic.gdx.tools.texturepacker.TexturePacker

object AssetPackerPlatformer {
    private const val source = "lwjgl3/assets-platformer-raw/"
    private const val destination = "assets-platformer"

    @JvmStatic
    fun main(args: Array<String>) {
        TexturePacker.process("$source/gameplay", "$destination/gameplay", "gameplay")

    }
}