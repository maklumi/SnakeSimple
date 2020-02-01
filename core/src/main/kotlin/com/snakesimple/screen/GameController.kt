package com.snakesimple.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.utils.Array
import com.snakesimple.common.GameManager
import com.snakesimple.common.GameState
import com.snakesimple.config.GameConfig
import com.snakesimple.entity.BodyPart
import com.snakesimple.entity.Coin
import com.snakesimple.entity.Direction
import com.snakesimple.entity.Snake

class GameController {

    val snake = Snake()
    val coin = Coin()
    private var timer = 0f

    fun update(delta: Float) {
        queryInput()

        if (GameManager.isReady() || GameManager.isGameOver()) return

        timer += delta
        if (timer > GameConfig.MOVE_TIME) {
            timer = 0f
            snake.move()
            checkBoundary()
            checkCollision()
        }

        spawnCoin()
    }

    private fun queryInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) snake.direction = Direction.LEFT
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) snake.direction = Direction.RIGHT
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) snake.direction = Direction.UP
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) snake.direction = Direction.DOWN
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) Gdx.app.exit()
        if (Gdx.input.isKeyPressed(Input.Keys.I)) snake.insertBodyPart()
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && !GameManager.isPlaying()) restart()
    }

    private fun checkBoundary() {
        if (snake.head.x >= GameConfig.WORLD_WIDTH) snake.head.x = 0f
        if (snake.head.x < 0f) snake.head.x = GameConfig.WORLD_WIDTH - snake.head.width
        if (snake.head.y >= GameConfig.MAX_Y) snake.head.y = 0f
        if (snake.head.y < 0f) snake.head.y = GameConfig.MAX_Y - snake.head.height
    }

    private fun spawnCoin() {
        if (!coin.available) {
            coin.available = true
            val x = MathUtils.random((GameConfig.WORLD_WIDTH - coin.width).toInt())
            val y = MathUtils.random((GameConfig.MAX_Y - coin.height).toInt())
//            val y = (GameConfig.MAX_Y - coin.height).toInt()
            coin.setPosition(x.toFloat(), y.toFloat())
        }
    }

    private fun checkCollision() {
        // check head <-> coin collision and add lengthen body part
        val headCoinCollision = Intersector.overlaps(snake.head.bounds, coin.bounds)
        if (headCoinCollision && coin.available) {
            snake.insertBodyPart()
            coin.available = false // reset so can be spawned again
            GameManager.score += GameConfig.COIN_SCORE
        }

        // check head <-> body part
        val bodyParts = Array.ArrayIterable<BodyPart>(snake.bodyParts)
        for (part in bodyParts) {
            if (part.justAdded) {
                part.justAdded = false
                continue // with other part
            }
            if (Intersector.overlaps(snake.head.bounds, part.bounds)) {
                GameManager.saveHighScore()
                GameManager.state = GameState.GAME_OVER
                coin.setPosition(-2f, 2f) // put it off screen
                GameManager.reset()
            }

        }
    }

    private fun restart() {
        snake.bodyParts.clear()
        snake.head.setPosition(0f, 0f)
        snake.direction = Direction.RIGHT
        coin.available = false
        GameManager.state = GameState.PLAYING
    }
}