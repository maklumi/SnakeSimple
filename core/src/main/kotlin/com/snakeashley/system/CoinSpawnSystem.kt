package com.snakeashley.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.MathUtils
import com.snakeashley.CoinComponent
import com.snakeashley.Position
import com.snakeashley.common.COIN
import com.snakeashley.common.POSITION
import com.snakesimple.config.GameConfig
import ktx.ashley.get


class CoinSpawnSystem : IteratingSystem(FAMILY) {

    companion object {
        private val FAMILY = Family.all(
                Position::class.java,
                CoinComponent::class.java
        ).get()
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val position = entity[POSITION]!!
        val coin = entity[COIN]!!

        if (coin.available) return

        // coin not available, reposition and make it available
        val coinX = MathUtils.random(GameConfig.WORLD_WIDTH - GameConfig.COIN_SIZE).toInt()
        val coinY = MathUtils.random(GameConfig.MAX_Y - GameConfig.COIN_SIZE).toInt()

        position.x = coinX.toFloat()
        position.y = coinY.toFloat()
        coin.available = true
    }


}