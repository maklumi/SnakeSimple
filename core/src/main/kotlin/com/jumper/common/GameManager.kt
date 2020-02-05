package com.jumper.common

import com.badlogic.gdx.Gdx
import com.snakesimple.common.GameManager


object GameManager {

    var score = 0
        set(value) {
            field = value
            if (field > highScore) highScore = field
        }

    var displayScore = 0

    private val prefs = Gdx.app.getPreferences("CircleJumperGame") // inside user/.prefs/
    private const val HIGH_SCORE = "highScore"
    private var highScore = prefs.getInteger(HIGH_SCORE, 0)
    var displayHighScore = highScore

    fun saveHighScore() {
        if (score < highScore) return

        highScore = score
        prefs.putInteger(HIGH_SCORE, highScore)
        prefs.flush()
    }

    fun displayScores(delta: Float) {
        if (displayScore < score)
            displayScore = score.coerceAtMost(displayScore + (60 * delta).toInt())

        if (displayHighScore < highScore)
            displayHighScore = highScore.coerceAtMost(displayHighScore + (60 * delta).toInt())
    }

    fun reset() {
        GameManager.score = 0
        GameManager.displayScore = 0
    }

}