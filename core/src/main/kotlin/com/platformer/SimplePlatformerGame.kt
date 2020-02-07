package com.platformer

import com.platformer.screen.GameScreen
import com.util.game.GameBase

class SimplePlatformerGame : GameBase() {

    override fun postCreate() {
        setScreen(GameScreen(this))
    }
}