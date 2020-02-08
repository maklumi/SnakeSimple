package com.platformer.screen.game

import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.utils.Array
import com.platformer.config.GameConfig
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

    var score = 0
    var lives = GameConfig.LIVES_START

    fun toggleDrawGrid() {
        isDrawGrid = !isDrawGrid
    }

    fun toggleDrawDebug() {
        isDrawDebug = !isDrawDebug
    }

    fun update(delta: Float) {
        player.update(delta)

        blockPlayerFromLeavingWorld()

        // check collision player-platform
        platforms.forEach { p ->
            if (Intersector.overlapConvexPolygons(player.bounds, p.bounds) && player.isFalling()) {
                player.y = p.y + p.height // top of platform
                player.jump()
            }
        }

        // player - coins
        coins.forEach { coin ->
            if (Intersector.overlapConvexPolygons(player.bounds, coin.bounds)) {
                coins.removeValue(coin, true)
                score += GameConfig.COIN_SCORE
            }
        }

        // player - hazards
        waterHazards.forEach { water ->
            val overlaps = Intersector.overlapConvexPolygons(player.bounds, water.bounds)
            if (overlaps) {
                player.die()
                lives--
                // spawn player again
                player.reset()
            }
        }

    }

    private fun blockPlayerFromLeavingWorld() {
        // left
        if (player.x < 0f) player.x = 0f

        // right
        val maxX = GameConfig.WORLD_WIDTH - player.width
        if (player.x > maxX) player.x = maxX

        // top
//        val maxY = GameConfig.WORLD_HEIGHT - player.height
//        if (player.y > maxY) player.y = maxY

    }

}