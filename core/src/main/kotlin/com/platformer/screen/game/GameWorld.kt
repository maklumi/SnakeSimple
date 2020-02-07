package com.platformer.screen.game

import com.badlogic.gdx.utils.Array
import com.platformer.common.EntityFactory
import com.platformer.entity.WaterHazard


class GameWorld {

    var isDrawGrid = false
    var isDrawDebug = true

    val waterHazards = Array<WaterHazard>().also {
        it.add(EntityFactory.createWaterHazard())
    }

    fun toggleDrawGrid() {
        isDrawGrid = !isDrawGrid
    }

    fun toggleDrawDebug() {
        isDrawDebug = !isDrawDebug
    }

    fun update(delta: Float) {

    }
}