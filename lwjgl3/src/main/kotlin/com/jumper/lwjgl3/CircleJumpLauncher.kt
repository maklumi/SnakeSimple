package com.jumper.lwjgl3

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.jumper.CircleJumperGame
import com.jumper.config.GameConfig

object CircleJumpLauncher {

    @JvmStatic
    fun main(args: Array<String>) {
        createApplication()
    }

    private fun createApplication() {
        Lwjgl3Application(CircleJumperGame(), defaultConfiguration)
    }

    private val defaultConfiguration: Lwjgl3ApplicationConfiguration
        get() {
            val configuration = Lwjgl3ApplicationConfiguration()
            configuration.setTitle("Circle Jump")
            configuration.setWindowedMode(GameConfig.WIDTH.toInt(), GameConfig.HEIGHT.toInt())
            configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png")
            configuration.setWindowPosition(5, 1030 - GameConfig.HEIGHT.toInt())
            return configuration
        }

}