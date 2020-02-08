package com.platformer.screen.game

import com.badlogic.gdx.utils.Array
import com.platformer.entity.Coin
import com.platformer.entity.Platform
import com.platformer.entity.Player
import com.platformer.entity.WaterHazard


class GameWorld {

    var isDrawGrid = false
    var isDrawDebug = true

    val waterHazards = Array<WaterHazard>()
    val platforms = Array<Platform>()
    var player = Player()
    val coins = Array<Coin>()

    var wait = 0f

    fun toggleDrawGrid() {
        isDrawGrid = !isDrawGrid
    }

    fun toggleDrawDebug() {
        isDrawDebug = !isDrawDebug
    }

    fun update(delta: Float) {
        wait += delta
    }
}