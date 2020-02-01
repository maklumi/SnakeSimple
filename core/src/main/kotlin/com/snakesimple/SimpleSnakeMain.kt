package com.snakesimple

import com.badlogic.gdx.Game
import com.snakesimple.screen.GameScreen

class SimpleSnakeMain : Game() {
    override fun create() {
        setScreen(GameScreen(this))
    }
}