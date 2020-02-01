package com.snakesimple.lwjgl3

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.snakesimple.SimpleSnakeMain
import com.snakesimple.config.GameConfig

/**
 * Launches the desktop (LWJGL3) application.
 */
object Lwjgl3Launcher {
    @JvmStatic
    fun main(args: Array<String>) {
        createApplication()
    }

    private fun createApplication() {
        Lwjgl3Application(SimpleSnakeMain(), defaultConfiguration)
    }

    private val defaultConfiguration: Lwjgl3ApplicationConfiguration
        get() {
            val configuration = Lwjgl3ApplicationConfiguration()
            configuration.setTitle("SnakeCircleJump")
            configuration.setWindowedMode(GameConfig.WIDTH.toInt(), GameConfig.HEIGHT.toInt())
            configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png")
            configuration.setWindowPosition(5, 1030 - GameConfig.HEIGHT.toInt())
            return configuration
        }
}