package com.platformer

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.platformer.config.GameConfig

object SimplePlatformerLauncher {
    @JvmStatic
    fun main(args: Array<String>) {
        createApplication()
    }

    private fun createApplication() {
        Lwjgl3Application(SimplePlatformerGame(), defaultConfiguration)
    }

    private val defaultConfiguration: Lwjgl3ApplicationConfiguration
        get() {
            val configuration = Lwjgl3ApplicationConfiguration()
            configuration.setTitle("Simple Platformer")
            configuration.setWindowedMode(GameConfig.WIDTH, GameConfig.HEIGHT)
            configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png")
            configuration.setWindowPosition(5, 1030 - GameConfig.HEIGHT)
            return configuration
        }

}