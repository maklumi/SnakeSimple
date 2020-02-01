package com.snakesimple.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.snakesimple.config.GameConfig
import com.snakesimple.entity.Direction
import com.snakesimple.entity.SnakeHead

class GameController {

    val head = SnakeHead()
    private var timer = 0f

    fun update(delta: Float) {
        queryInput()

        timer += delta
        if (timer > GameConfig.MOVE_TIME) {
            timer = 0f
            head.move()
            checkBoundary()
        }
    }

    private fun queryInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) head.direction = Direction.LEFT
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) head.direction = Direction.RIGHT
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) head.direction = Direction.UP
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) head.direction = Direction.DOWN
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) Gdx.app.exit()
    }

    private fun checkBoundary() {
        if (head.x >= GameConfig.WORLD_WIDTH) head.x = 0f
        if (head.x < 0f) head.x = GameConfig.WORLD_WIDTH - head.width
        if (head.y >= GameConfig.WORLD_HEIGHT) head.y = 0f
        if (head.y < 0f) head.y = GameConfig.WORLD_HEIGHT - head.height
    }

}