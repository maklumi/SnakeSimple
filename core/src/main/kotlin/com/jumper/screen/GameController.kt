package com.jumper.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.MathUtils
import com.jumper.config.GameConfig
import com.jumper.entity.Monster
import com.jumper.entity.Planet
import com.badlogic.gdx.utils.Array
import com.jumper.entity.Coin

class GameController {

    val planet = Planet().apply {
        setSize(GameConfig.PLANET_SIZE, GameConfig.PLANET_SIZE)
        setPosition(GameConfig.WORLD_CENTER_X, GameConfig.WORLD_CENTER_Y)
    }

    val monster = Monster().apply {
        setPosition(GameConfig.WORLD_CENTER_X - GameConfig.MONSTER_HALF_SIZE, GameConfig.WORLD_CENTER_Y + GameConfig.PLANET_HALF_SIZE)
    }

    val coins = Array<Coin>()
    private var coinTimer = 0f

    fun update(delta: Float) {
        monster.update(delta)

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && monster.isWalking) {
            monster.jump()
        }

        coins.forEach { coin -> coin.update(delta) }
        spawnCoin(delta)
    }

    private fun spawnCoin(delta: Float) {
        coinTimer += delta
        if (coinTimer < GameConfig.COIN_SPAWN_TIME) {
            return
        }

        coinTimer = 0f
        addCoin()
    }

    private fun addCoin() {
        val randomAngle = MathUtils.random(360f)
        val coin = Coin()
        coin.offset = true
        coin.angleDeg = randomAngle
        coins.add(coin)
    }

}