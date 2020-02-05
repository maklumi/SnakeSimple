package com.jumper.common

import com.snakesimple.common.GameManager


object GameManager {

    var score = 0
        set(value) {
            field = value
            if (field > highScore) highScore = field
        }

    private var highScore = 0

    var displayScore = 0
    var displayHighScore = 0

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