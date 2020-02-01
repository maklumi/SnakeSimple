package com.snakesimple.common

object GameManager {

    var score = 0
        set(value) {
            field = value
            if (field > highScore) highScore = field
        }

    var highScore = 0

    var displayScore = 0
    var displayHighScore = 0

    fun displayScores(delta: Float) {
        if (displayScore < score) {
            displayScore = score.coerceAtMost(displayScore + (60 * delta).toInt())
        }

        if (displayHighScore < highScore) {
            displayHighScore = highScore.coerceAtMost(displayHighScore + (60 * delta).toInt())
        }
    }

    fun reset() {
        score = 0
        displayScore = 0
    }

    var state = GameState.READY

    fun isGameOver(): Boolean = state == GameState.GAME_OVER

    fun isReady(): Boolean = state == GameState.READY

    fun isPlaying(): Boolean = state == GameState.PLAYING

}