package com.brickbreaker.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool
import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array
import com.brickbreaker.common.ScoreController
import com.brickbreaker.config.GameConfig
import com.brickbreaker.entity.EntityFactory
import com.brickbreaker.entity.Pickup
import com.brickbreaker.input.PaddleInputController
import com.brickbreaker.util.shape.RectangleUtils

class GameController(private val factory: EntityFactory,
                     val scoreController: ScoreController) {

    val pickups = Array<Pickup>()
    val effects = Array<ParticleEffectPool.PooledEffect>()
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
        checkPaddleWithPickupCollision()
        if (bricks.isEmpty) startLevel()
        updateEffects(delta)
        updatePickups(delta)
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

            // create fire effect
            val effectX = brick.x + brick.width / 2f
            val effectY = brick.y + brick.height / 2f
            val effect = factory.createFire(effectX, effectY)
            effects.add(effect)

            // create pick ups
            val pickupX = brick.x + (brick.width - GameConfig.PICKUP_SIZE) / 2f
            val pickup = factory.createPickup(pickupX, effectY)
            pickups.add(pickup)

            // add score
            scoreController.score += GameConfig.BRICK_SCORE
            scoreController.updateHighScore()
        }
    }

    private fun checkPaddleWithPickupCollision() {
        val iterator = Array.ArrayIterator(pickups)
        while (iterator.hasNext()) {
            val pickup = iterator.next()

            if (Intersector.overlapConvexPolygons(paddle.bounds, pickup.bounds)) {
                iterator.remove()
                factory.freePickup(pickup)
            }
        }
    }

    private fun updateEffects(delta: Float) {
        val iterator = Array.ArrayIterator(effects)
        while (iterator.hasNext()) {
            val effect = iterator.next()
            effect.update(delta)

            if (effect.isComplete) {
                iterator.remove()
                effect.free()
            }
        }
    }

    private fun updatePickups(delta: Float) {
        val iterator = Array.ArrayIterator(pickups)
        while (iterator.hasNext()) {
            val pickup = iterator.next()
            pickup.update(delta)

            if (pickup.y < -pickup.height) {
                factory.freePickup(pickup)
                iterator.remove()
            }
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
        for (i in 0 until effects.size) {
            val effect = effects.get(i)
            effect.free()
            effects.removeIndex(i)
        }
    }

}