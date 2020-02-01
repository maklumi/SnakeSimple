package com.snakesimple

import com.badlogic.gdx.Application
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.snakesimple.screen.GameScreen

@Suppress("LibGDXLogLevel")
class SimpleSnakeMain : Game() {

    val assetManager = AssetManager()
    lateinit var batch: SpriteBatch

    override fun create() {
        Gdx.app.logLevel = Application.LOG_DEBUG
        batch = SpriteBatch()
        setScreen(GameScreen(this))
    }

    override fun dispose() {
        assetManager.dispose()
        batch.dispose()
    }
}