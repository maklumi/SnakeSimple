package com.brickbreaker.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.Vector2
import com.brickbreaker.common.ScoreController
import com.brickbreaker.config.GameConfig
import com.brickbreaker.entity.EntityFactory
import com.brickbreaker.input.PaddleInputController
import com.brickbreaker.util.shape.RectangleUtils

class GameController(private val factory: EntityFactory,
                     private val scoreController: ScoreController) {

    val ball = factory.createBall()
    val bricks = factory.createBricks()
    val paddle = factory.createPaddle()
    private val paddleInputController = PaddleInputController(paddle)

    fun update(delta: Float) {
        if (Gdx.input.justTouched() && ball.isNotActive) {
            startLevel()
            ball.setVelocityY(GameConfig.BALL_START_SPEED)
        }

        if (ball.isNotActive) return
        paddleInputController.update(delta)
        paddle.limitX()
        ball.update(delta)
        limitBallXY()
        checkPaddleCollision()
        checkBrickCollision()
        if (bricks.isEmpty) startLevel()
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

    private fun checkPaddleCollision() {
        if (Intersector.overlapConvexPolygons(ball.bounds, paddle.bounds)) {
            val ballCenterX = ball.x + ball.width / 2f
            val percent = (ballCenterX - paddle.x) / paddle.width // 0f-1f
            // interpolate angle between 150 and 30
            val bounceAngle = 150 - percent * 120
            ball.setVelocity(bounceAngle, ball.speed)
        }
    }

    private fun checkBrickCollision() {
        val ballPolygon = ball.bounds
        val ballRadius = ball.width / 2f
        ball.bound.set(ball.x + ballRadius, ball.y + ballRadius, ballRadius)
        val ballBounds = ball.bound

        for (brick in bricks.asSequence()) {
            val brickPolygon = brick.bounds
            val brickBounds = brickPolygon.boundingRectangle

            if (!Intersector.overlapConvexPolygons(ballPolygon, brickPolygon)) {
                continue
            }
            bricks.removeValue(brick, true)

            // check which side of brick is overlapping with ball
            val bL = RectangleUtils.getBottomLeft((brickBounds))
            val bR = RectangleUtils.getBottomRight(brickBounds)
            val tL = RectangleUtils.getTopLeft(brickBounds)
            val tR = RectangleUtils.getTopRight(brickBounds)

            val center = Vector2(ballBounds.x, ballBounds.y)
            val sqRadius = ballBounds.radius * ballBounds.radius

            val bottomHit = Intersector.intersectSegmentCircle(bL, bR, center, sqRadius)
            val topHit = Intersector.intersectSegmentCircle(tL, tR, center, sqRadius)
            val leftHit = Intersector.intersectSegmentCircle(bL, tL, center, sqRadius)
            val rightHit = Intersector.intersectSegmentCircle(bR, tR, center, sqRadius)

            // left - right
            if (ball.velocity.x > 0 && leftHit) {
                ball.multiplyVelocityX(-1f)
            } else if (ball.velocity.x < 0 && rightHit) {
                ball.multiplyVelocityX(-1f)
            }

            // bottom - top
            if (ball.velocity.y > 0 && bottomHit) {
                ball.multiplyVelocityY(-1f)
            } else if (ball.velocity.y < 0 && topHit) {
                ball.multiplyVelocityY(-1f)
            }


            // add score
            scoreController.score += GameConfig.BRICK_SCORE
            scoreController.updateHighScore()
            println("Score: ${scoreController.score} HighScore: ${scoreController.highScore}")
        }
    }

    private fun startLevel() {
        scoreController.reset()
        restart()
        bricks.clear()
        bricks.addAll(factory.createBricks())
    }

    private fun restart() {
        paddle.setPosition(GameConfig.PADDLE_START_X, GameConfig.PADDLE_START_Y)
        ball.setPosition(GameConfig.BALL_START_X, GameConfig.BALL_START_Y)
        ball.bound.setPosition(ball.x + GameConfig.BALL_HALF_SIZE, ball.y + GameConfig.BALL_HALF_SIZE)
        ball.stop()
    }

}