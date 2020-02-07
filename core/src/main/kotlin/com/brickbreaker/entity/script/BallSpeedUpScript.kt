package com.brickbreaker.entity.script

import com.brickbreaker.config.GameConfig
import com.brickbreaker.entity.Ball
import com.util.entity.script.EntityScriptBase

class BallSpeedUpScript : EntityScriptBase<Ball>() {

    private var finalSpeed = 0f

    override fun added(entity: Ball) {
        super.added(entity)

        val currentSpeed = entity.speed
        finalSpeed = currentSpeed + GameConfig.BALL_START_SPEED * GameConfig.BALL_SPEED_FACTOR

        if (finalSpeed >= GameConfig.BALL_MAX_SPEED) {
            finalSpeed = GameConfig.BALL_MAX_SPEED
        }
    }

    override fun update(delta: Float) {
        if (entity == null) return
        val angleDeg = entity!!.angleDeg
        entity!!.setVelocity(angleDeg, finalSpeed)
        finish()
    }

}
