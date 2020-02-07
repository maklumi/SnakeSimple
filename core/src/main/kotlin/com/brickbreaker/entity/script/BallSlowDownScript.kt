package com.brickbreaker.entity.script

import com.badlogic.gdx.utils.Logger
import com.brickbreaker.config.GameConfig
import com.brickbreaker.entity.Ball

class BallSlowDownScript : EntityScriptBase<Ball>() {

    private var finalSpeed = 0f
    private val log = Logger(BallSlowDownScript::class.java.simpleName, Logger.DEBUG)

    override fun added(entity: Ball) {
        super.added(entity)

        val currentSpeed = entity.speed
        finalSpeed = currentSpeed - GameConfig.BALL_START_SPEED * GameConfig.BALL_SPEED_FACTOR

        if (finalSpeed <= GameConfig.BALL_MIN_SPEED) {
            finalSpeed = GameConfig.BALL_MIN_SPEED
        }
    }

    override fun update(delta: Float) {
        val angleDeg = entity!!.angleDeg

//        log.debug("speed before update= " + entity!!.speed)

        entity!!.setVelocity(angleDeg, finalSpeed)

        log.debug("speed after update= " + entity!!.speed)

        finish()
    }

}
