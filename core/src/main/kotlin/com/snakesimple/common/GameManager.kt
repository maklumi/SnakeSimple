package com.snakesimple.common

import com.badlogic.gdx.Gdx

object GameManager {

    var score = 0
        set(value) {
            field = value
            if (field > highScore) highScore = field
        }

    private var highScore = 0

    var displayScore = 0
    var displayHighScore = 0

    private val prefs = Gdx.app.getPreferences("SimpleSnake") // user/.prefs/SimpleSnake
    private const val HIGH_SCORE = "highScore"

    init {
        highScore = prefs.getInteger(HIGH_SCORE, 0)
        displayHighScore = highScore
    }

    fun saveHighScore() {
        if (score < highScore) return

        highScore = score
        prefs.putInteger(HIGH_SCORE, highScore)
        prefs.flush()
    }

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