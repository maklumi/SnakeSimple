package com.jumper

import com.badlogic.gdx.Game
import com.badlogic.gdx.assets.AssetManager
import com.jumper.screen.GameScreen

class CircleJumperGame : Game() {

    val assetManager = AssetManager()

    override fun create() {
        setScreen(GameScreen(this))
    }
}