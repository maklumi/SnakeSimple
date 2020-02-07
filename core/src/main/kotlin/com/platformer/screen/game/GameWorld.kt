package com.platformer.screen.game


class GameWorld {

    var isDrawGrid = true
    var isDrawDebug = true

    fun toggleDrawGrid() {
        isDrawGrid = !isDrawGrid
    }

    fun toggleDrawDebug() {
        isDrawDebug = !isDrawDebug
    }

    fun update(delta: Float) {

    }
}