package com.brickbreaker

import com.brickbreaker.screen.LoadingScreen
import com.util.game.GameBase

class BrickBreakerGame : GameBase() {

    override fun postCreate() {
        setScreen(LoadingScreen(this))
    }

}