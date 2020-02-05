package com.jumper

import com.jumper.screen.LoadingScreen
import com.util.game.GameBase

class CircleJumperGame : GameBase() {

    override fun postCreate() {
        setScreen(LoadingScreen(this))
    }
}