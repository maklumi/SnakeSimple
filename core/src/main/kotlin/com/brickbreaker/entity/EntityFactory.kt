package com.brickbreaker.entity

import com.badlogic.gdx.utils.Array
import com.brickbreaker.config.GameConfig

class EntityFactory {

    fun createPaddle(): Paddle {
        val paddle = Paddle()
        paddle.setPosition(GameConfig.PADDLE_START_X, GameConfig.PADDLE_START_Y)
        paddle.setSize(GameConfig.PADDLE_START_WIDTH, GameConfig.PADDLE_HEIGHT)
        return paddle
    }

    fun createBricks(): Array<Brick> {
        val bricks = Array<Brick>()

        val startX = GameConfig.LEFT_PAD
        val startY = GameConfig.WORLD_HEIGHT - (GameConfig.TOP_PAD + GameConfig.BRICK_HEIGHT)

        for (row in 0 until GameConfig.ROW_COUNT) {
            val brickY = startY - row * (GameConfig.ROW_SPACING + GameConfig.BRICK_HEIGHT)

            for (column in 0 until GameConfig.COLUMN_COUNT) {
                val brickX = startX + column * (GameConfig.BRICK_WIDTH + GameConfig.COLUMN_SPACING)

                bricks.add(createBrick(brickX, brickY))
            }
        }

        return bricks
    }

    private fun createBrick(x: Float, y: Float): Brick {
        val brick = Brick()
        brick.setPosition(x, y)
        brick.setSize(GameConfig.BRICK_WIDTH, GameConfig.BRICK_HEIGHT)
        return brick
    }
}