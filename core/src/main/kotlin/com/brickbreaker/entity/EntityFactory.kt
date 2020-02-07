package com.brickbreaker.entity

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.Pools
import com.brickbreaker.assets.AssetDescriptors
import com.brickbreaker.config.GameConfig

class EntityFactory(assetManager: AssetManager) {

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

    fun createBall(): Ball {
        val ball = Ball()
        ball.setPosition(GameConfig.BALL_START_X, GameConfig.BALL_START_Y)
        ball.setSize(GameConfig.BALL_SIZE)
        ball.setVelocity(GameConfig.BALL_START_ANGLE, GameConfig.BALL_START_SPEED)
        return ball
    }

    private val fireEffect = assetManager.get(AssetDescriptors.FIRE)
    private val fireEffectPool = ParticleEffectPool(fireEffect, 5, 20)

    fun createFire(x: Float, y: Float): ParticleEffectPool.PooledEffect {
        val fireEffect = fireEffectPool.obtain()
        fireEffect.setPosition(x, y)
        fireEffect.start()
        return fireEffect
    }

    private val pickupPool = Pools.get(Pickup::class.java, 10)

    fun createPickup(x: Float, y: Float): Pickup {
        val pickup = pickupPool.obtain()
        pickup.type = PickupType.random()
        pickup.setSize(GameConfig.PICKUP_SIZE, GameConfig.PICKUP_SIZE)
        pickup.setPosition(x, y)
        pickup.setVelocityY(GameConfig.PICKUP_VELOCITY_Y)
        return pickup
    }

    fun freePickup(pickup: Pickup) {
        pickupPool.free(pickup)
    }

}