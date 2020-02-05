package com.jumper.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.Pools
import com.jumper.common.GameManager
import com.jumper.config.GameConfig
import com.jumper.entity.Coin
import com.jumper.entity.Monster
import com.jumper.entity.Obstacle
import com.jumper.entity.Planet

class GameController {

    val planet = Planet().apply {
        setSize(GameConfig.PLANET_SIZE, GameConfig.PLANET_SIZE)
        setPosition(GameConfig.WORLD_CENTER_X, GameConfig.WORLD_CENTER_Y)
    }

    val monster = Monster()

    val coins = Array<Coin>()
    private val coinPool = Pools.get(Coin::class.java, 10)
    private var coinTimer = 0f
    val obstacles = Array<Obstacle>()
    private val obstaclePool = Pools.get(Obstacle::class.java, 10)
    private var obstacleTimer = 0f

    var startWaitTimer = GameConfig.START_WAIT_TIME

    fun update(delta: Float) {
        if (startWaitTimer > 0f) {
            startWaitTimer -= delta
            return
        }
        monster.update(delta)

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && monster.isWalking) {
            monster.jump()
        }

        coins.forEach { coin -> coin.update(delta) }
        obstacles.forEach { obstacle -> obstacle.update(delta) }
        spawnCoin(delta)
        spawnObstacles(delta)
        checkCollision()
        GameManager.displayScores(delta)
    }

    private fun spawnCoin(delta: Float) {
        coinTimer += delta
        if (coins.size >= GameConfig.MAX_COINS) {
            coinTimer = 0f
            return
        }
        if (coinTimer < GameConfig.COIN_SPAWN_TIME) {
            return
        }

        coinTimer = 0f
        addCoin()
    }

    private fun addCoin() {
        val randomAngle = MathUtils.random(360f)
        val coin = coinPool.obtain()
        coin.offset = true
        coin.angleDeg = randomAngle
        coins.add(coin)
    }

    private fun spawnObstacles(delta: Float) {
        obstacleTimer += delta

        // only max obstacles allowed
        if (obstacles.size >= GameConfig.MAX_OBSTACLES) {
            obstacleTimer = 0f
            return
        }

        if (obstacleTimer > GameConfig.OBSTACLE_SPAWN_TIME) {
            obstacleTimer = 0f
            val obstacle = obstaclePool.obtain()
            val randomAngle = MathUtils.random(360f)
            obstacle.angleDeg = randomAngle
            obstacles.add(obstacle)
        }

    }

    private fun checkCollision() {
        // player <-> coins
        val coins = Array.ArrayIterable<Coin>(coins)
        for (coin in coins) {
            if (Intersector.overlaps(monster.bounds, coin.bounds)) {
                GameManager.score += GameConfig.COIN_SCORE
                coinPool.free(coin)
                this.coins.removeValue(coin, true)
            }
        }

        // player <-> obstacle
        val obstacles = Array.ArrayIterable<Obstacle>(this.obstacles)
        for (obstacle in obstacles) {
            if (Intersector.overlaps(monster.bounds, obstacle.sensor)) {
                GameManager.score += GameConfig.OBSTACLE_SCORE
                obstaclePool.free(obstacle)
                this.obstacles.removeValue(obstacle, true)
            } else if (Intersector.overlaps(monster.bounds, obstacle.bounds)) {
                restart()
            }
        }
    }

    private fun restart() {
        coinPool.freeAll(coins)
        coins.clear()
        obstaclePool.freeAll(obstacles)
        obstacles.clear()
        monster.reset()
        GameManager.reset()
        startWaitTimer = GameConfig.START_WAIT_TIME
    }

}