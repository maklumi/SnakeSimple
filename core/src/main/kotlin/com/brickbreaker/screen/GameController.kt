package com.brickbreaker.screen

import com.badlogic.gdx.math.Intersector
import com.brickbreaker.config.GameConfig
import com.brickbreaker.entity.EntityFactory
import com.brickbreaker.input.PaddleInputController

class GameController(factory: EntityFactory) {

    val ball = factory.createBall()
    val bricks = factory.createBricks()
    val paddle = factory.createPaddle()
    private val paddleInputController = PaddleInputController(paddle)

    fun update(delta: Float) {
        paddleInputController.update(delta)
        paddle.limitX()
        ball.update(delta)
        limitBallXY()
        checkCollision()
    }

    private fun limitBallXY() {
        if (ball.y <= 0f) {
            ball.y = 0f
            ball.multiplyVelocityY(-1f)
        }
        if (ball.y >= GameConfig.WORLD_HEIGHT - GameConfig.BALL_SIZE) {
            ball.y = GameConfig.WORLD_HEIGHT - GameConfig.BALL_SIZE
            ball.multiplyVelocityY(-1f)
        }
        if (ball.x <= 0) {
            ball.x = 0f
            ball.multiplyVelocityX(-1f)
        }
        if (ball.x > GameConfig.WORLD_WIDTH - GameConfig.BALL_SIZE) {
            ball.x = GameConfig.WORLD_WIDTH - GameConfig.BALL_SIZE
            ball.multiplyVelocityX(-1f)
        }
        ball.bound.setPosition(ball.x, ball.y)
    }

    private fun checkCollision() {
        if (Intersector.overlapConvexPolygons(ball.bounds, paddle.bounds)) {
            val ballCenterX = ball.x + ball.width / 2f
            val percent = (ballCenterX - paddle.x) / paddle.width // 0f-1f
            // interpolate angle between 150 and 30
            val bounceAngle = 150 - percent * 120
            ball.setVelocity(bounceAngle, ball.speed)
        }
    }
}