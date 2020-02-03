package com.jumper

import com.jumper.screen.GameScreen
import com.util.game.GameBase

class CircleJumperGame : GameBase() {

    override fun postCreate() {
        setScreen(GameScreen(this))
    }
}