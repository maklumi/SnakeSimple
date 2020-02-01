package com.snakesimple.common

object GameManager {

    var state = GameState.READY

    fun isGameOver(): Boolean = state == GameState.GAME_OVER

    fun isReady(): Boolean = state == GameState.READY

    fun isPlaying(): Boolean = state == GameState.PLAYING

}