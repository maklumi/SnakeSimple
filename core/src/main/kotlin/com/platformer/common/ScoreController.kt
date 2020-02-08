package com.platformer.common

import com.badlogic.gdx.Gdx
import com.platformer.SimplePlatformerGame


object ScoreController {

    private val prefs = Gdx.app.getPreferences(SimplePlatformerGame::class.java.name)
    private const val HIGH_SCORE_KEY = "highScore"

    var score = 0
    var highScore = prefs.getInteger(HIGH_SCORE_KEY, 0)

    fun reset() {
        score = 0
    }

    fun updateHighScore() {
        if (score < highScore) return

        highScore = score
        prefs.putInteger(HIGH_SCORE_KEY, highScore)
        prefs.flush()
    }

}
