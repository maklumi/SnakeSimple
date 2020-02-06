package com.jumper.common

enum class GameState {
    MENU,
    READY,
    PLAYING,
    GAME_OVER;

    fun isMenu(): Boolean {
        return this === MENU
    }

    fun isReady(): Boolean {
        return this === READY
    }

    fun isPlaying(): Boolean {
        return this === PLAYING
    }

    fun isGameOver(): Boolean {
        return this === GAME_OVER
    }

    fun isPlayingOrReady(): Boolean {
        return isPlaying() || isReady()
    }
}