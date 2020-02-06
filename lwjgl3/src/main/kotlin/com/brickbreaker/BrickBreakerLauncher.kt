package com.brickbreaker

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.brickbreaker.config.GameConfig


object BrickBreakerLauncher {

    @JvmStatic
    fun main(args: Array<String>) {
        createApplication()
    }

    private fun createApplication() {
        Lwjgl3Application(BrickBreakerGame(), defaultConfiguration)
    }

    private val defaultConfiguration: Lwjgl3ApplicationConfiguration
        get() {
            val configuration = Lwjgl3ApplicationConfiguration()
            configuration.setTitle("Brick Breaker Game")
            configuration.setWindowedMode(GameConfig.WIDTH, GameConfig.HEIGHT)
            configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png")
            configuration.setWindowPosition(5, 1030 - GameConfig.HEIGHT)
            return configuration
        }

}