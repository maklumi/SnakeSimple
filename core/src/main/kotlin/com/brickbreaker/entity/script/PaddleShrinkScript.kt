package com.brickbreaker.entity.script

import com.brickbreaker.config.GameConfig
import com.brickbreaker.entity.Paddle
import com.util.entity.script.EntityScriptBase

class PaddleShrinkScript : EntityScriptBase<Paddle>() {

    private var finalWidth = 0f
    private var shouldFinish = false

    override fun added(entity: Paddle) {
        super.added(entity)

        val currentWidth = entity.width
        finalWidth = currentWidth - GameConfig.PADDLE_START_WIDTH * GameConfig.PADDLE_RESIZE_FACTOR

        if (finalWidth <= GameConfig.PADDLE_MIN_WIDTH) {
            finalWidth = GameConfig.PADDLE_MIN_WIDTH
        }
    }

    override fun update(delta: Float) {
        if (isFinished || entity == null) return

        val currentWidth = entity!!.width
        var newWidth = currentWidth - GameConfig.PADDLE_EXPAND_SHRINK_SPEED * delta

        if (newWidth <= finalWidth) {
            newWidth = finalWidth
            shouldFinish = true
        }

        entity!!.width = newWidth
        if (shouldFinish) finish()
    }

}
