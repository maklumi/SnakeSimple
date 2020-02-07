package com.brickbreaker

import com.badlogic.gdx.tools.texturepacker.TexturePacker

object AssetPackerBrickBreaker {

    private const val source = "lwjgl3/assets-brickbreaker-raw/"
    private const val destination = "assets-brickbreaker"

    @JvmStatic
    fun main(args: Array<String>) {
        TexturePacker.process("$source/gameplay", "$destination/gameplay", "gameplay")

        TexturePacker.process(
                "$source/ui",
                "$destination/ui",
                "skin"
        )
    }

}