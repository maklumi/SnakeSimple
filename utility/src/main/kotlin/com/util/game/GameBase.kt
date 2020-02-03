package com.util.game

import com.badlogic.gdx.Application
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.Logger

abstract class GameBase : Game() {

    val assetManager = AssetManager()
    lateinit var batch: SpriteBatch

    override fun create() {
        Gdx.app.logLevel = Application.LOG_DEBUG
        assetManager.logger.level = Logger.DEBUG
        batch = SpriteBatch()
        postCreate()
    }

    abstract fun postCreate()

    override fun dispose() {
        assetManager.dispose()
        batch.dispose()
    }
}