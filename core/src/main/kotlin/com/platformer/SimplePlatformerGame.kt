package com.platformer

import com.platformer.screen.LoadingScreen
import com.util.game.GameBase

class SimplePlatformerGame : GameBase() {

    override fun postCreate() {
        setScreen(LoadingScreen(this))
    }
}