package com.brickbreaker

import com.brickbreaker.screen.GameScreen
import com.util.game.GameBase

class BrickBreakerGame : GameBase() {

    override fun postCreate() {
        setScreen(GameScreen(this))
    }

}