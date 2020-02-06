package com.brickbreaker.common

import com.badlogic.gdx.Gdx
import com.brickbreaker.BrickBreakerGame

class ScoreController {


    private val highScoreKey = "highScore"

    private val prefs = Gdx.app.getPreferences(BrickBreakerGame::class.java.simpleName)

    var score = 0
    var highScore = prefs.getInteger(highScoreKey, 0)

    fun updateHighScore() {
        if (score < highScore) return

        highScore = score
        prefs.putInteger(highScoreKey, highScore)
        prefs.flush()
    }

    fun reset() {
        score = 0
    }

}
