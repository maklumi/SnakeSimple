package com.snakesimple.common

object GameManager {

    var score = 0
        set(value) {
            field = value
            if (field > highScore) highScore = field
        }

    var highScore = 0

    var state = GameState.READY

    fun isGameOver(): Boolean = state == GameState.GAME_OVER

    fun isReady(): Boolean = state == GameState.READY

    fun isPlaying(): Boolean = state == GameState.PLAYING

}