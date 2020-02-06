package com.jumper.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.Pools
import com.jumper.common.GameManager
import com.jumper.common.GameState
import com.jumper.config.GameConfig
import com.jumper.entity.Coin
import com.jumper.entity.Monster
import com.jumper.entity.Obstacle
import com.jumper.entity.Planet
import com.jumper.screen.menu.OverlayCallback
import kotlin.math.abs


class GameController {

    val planet = Planet().apply {
        setSize(GameConfig.PLANET_SIZE, GameConfig.PLANET_SIZE)
        setPosition(GameConfig.WORLD_CENTER_X - GameConfig.PLANET_HALF_SIZE, GameConfig.WORLD_CENTER_Y - GameConfig.PLANET_HALF_SIZE)
    }

    val monster = Monster()

    val coins = Array<Coin>()
    private val coinPool = Pools.get(Coin::class.java, 10)
    private var coinTimer = 0f
    val obstacles = Array<Obstacle>()
    private val obstaclePool = Pools.get(Obstacle::class.java, 10)
    private var obstacleTimer = 0f

    var startWaitTimer = GameConfig.START_WAIT_TIME

    var gameState = GameState.MENU

    val callback = object : OverlayCallback {
        override fun home() {
            gameState = GameState.MENU
        }

        override fun ready() {
            gameState = GameState.READY
        }
    }

    fun update(delta: Float) {
        if (gameState.isReady() && startWaitTimer > 0f) {
            startWaitTimer -= delta
            if (startWaitTimer <= 0f) gameState = GameState.PLAYING
        }

        if (!gameState.isPlaying()) return

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

        if (coinTimer < GameConfig.COIN_SPAWN_TIME) {
            return
        }

        coinTimer = 0f
        if (coins.isEmpty) addCoin()
    }

    private fun addCoin() {
        val count = MathUtils.random(GameConfig.MAX_COINS)
        repeat(count) {
            val randomAngle = MathUtils.random(360f)

            val canSpawn = !isCoinNearBy(randomAngle) && !isMonsterNearBy(randomAngle)
            if (canSpawn) {
                val coin = coinPool.obtain()
                if (isObstacleNearBy(randomAngle)) coin.offset = true
                coin.angleDeg = randomAngle
                coins.add(coin)
            }
        }
    }

    private fun spawnObstacles(delta: Float) {
        obstacleTimer += delta

        if (obstacleTimer > GameConfig.OBSTACLE_SPAWN_TIME) {
            obstacleTimer = 0f
            if (obstacles.isEmpty) addObstacles()
        }

    }

    private fun addObstacles() {
        val count = MathUtils.random(2, GameConfig.MAX_OBSTACLES)
        for (i in 0 until count) {
            val randomAngle = monster.angleDeg - i * GameConfig.MIN_ANG_DIST - MathUtils.random(60, 80)
            val canSpawn = (!isObstacleNearBy(randomAngle)
                    && !isCoinNearBy(randomAngle)
                    && !isMonsterNearBy(randomAngle))
            if (canSpawn) {
                val obstacle = obstaclePool.obtain()
                obstacle.angleDeg = randomAngle
                obstacles.add(obstacle)
            }
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
                gameState = GameState.GAME_OVER
            }
        }
    }

    private fun restart() {
        coinPool.freeAll(coins)
        coins.clear()
        obstaclePool.freeAll(obstacles)
        obstacles.clear()
        monster.reset()
        GameManager.saveHighScore()
        GameManager.reset()
        startWaitTimer = GameConfig.START_WAIT_TIME
        gameState = GameState.READY
    }

    private fun isCoinNearBy(angle: Float): Boolean {
        // check that there are no coins nearby min dist
        return coins.any { coin ->
            val angleDeg: Float = coin.angleDeg
            val diff = abs(abs(angleDeg) - abs(angle))
            diff < GameConfig.MIN_ANG_DIST
        }
    }

    private fun isMonsterNearBy(angle: Float): Boolean {
        val playerDiff = abs(abs(monster.angleDeg) - abs(angle))
        return playerDiff < GameConfig.MIN_ANG_DIST
    }

    private fun isObstacleNearBy(angle: Float): Boolean {
        return obstacles.any { obstacle ->
            val angleDeg = abs(obstacle.angleDeg)
            val diff = abs(angleDeg - abs(angle))
            diff < GameConfig.MIN_ANG_DIST
        }
    }

}