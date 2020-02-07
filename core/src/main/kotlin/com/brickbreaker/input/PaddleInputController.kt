package com.brickbreaker.input

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.brickbreaker.config.GameConfig
import com.brickbreaker.entity.Paddle
import com.brickbreaker.screen.GameView


class PaddleInputController(
        private val paddle: Paddle,
        private val view: GameView
) {

    private val halfWorldWidth = GameConfig.WORLD_WIDTH / 2f
    private val screenLeftSide = Rectangle(0f, 0f, halfWorldWidth, GameConfig.WORLD_HEIGHT)
    private val screenRightSide = Rectangle(halfWorldWidth, 0f, halfWorldWidth, GameConfig.WORLD_HEIGHT)

    fun update(delta: Float) {
        var velX = 0f

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            velX = -GameConfig.PADDLE_VELOCITY_X
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            velX = GameConfig.PADDLE_VELOCITY_X
        }

        if (Gdx.input.isTouched) {
            val screenX = Gdx.input.x // in pixels
            val screenY = Gdx.input.y // in pixels

            val screenPixel = Vector2(screenX.toFloat(), screenY.toFloat())
            val worldCoordinates = screenToWorld(screenPixel) // world units

            if (screenLeftSide.contains(worldCoordinates)) { // left side touch
                velX = -GameConfig.PADDLE_VELOCITY_X
            } else if (screenRightSide.contains(worldCoordinates)) { // right side touch
                velX = GameConfig.PADDLE_VELOCITY_X
            }
        }

        paddle.setVelocityX(velX)
        paddle.update(delta)
    }

    private fun screenToWorld(screenCoordinates: Vector2): Vector2 {
        return view.viewport.unproject(screenCoordinates)
    }
}