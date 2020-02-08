package com.platformer.screen.game

import com.badlogic.gdx.math.Intersector
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

    fun toggleDrawGrid() {
        isDrawGrid = !isDrawGrid
    }

    fun toggleDrawDebug() {
        isDrawDebug = !isDrawDebug
    }

    fun update(delta: Float) {
        player.update(delta)

        // check collision player-platform
        platforms.forEach { p ->
            if (Intersector.overlapConvexPolygons(player.bounds, p.bounds))
                player.jump()
        }
    }
}