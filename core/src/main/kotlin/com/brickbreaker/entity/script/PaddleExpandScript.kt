package com.brickbreaker.entity.script

import com.brickbreaker.config.GameConfig
import com.brickbreaker.entity.Paddle
import com.util.entity.script.EntityScriptBase

class PaddleExpandScript : EntityScriptBase<Paddle>() {

    private var finalWidth = 0f

    override fun added(entity: Paddle) {
        super.added(entity)

        val currentWidth = entity.width
        finalWidth = currentWidth + GameConfig.PADDLE_START_WIDTH * GameConfig.PADDLE_RESIZE_FACTOR

        if (finalWidth >= GameConfig.PADDLE_MAX_WIDTH) finalWidth = GameConfig.PADDLE_MAX_WIDTH

    }

    override fun update(delta: Float) {
        if (entity == null) return
        if (isFinished) return

        val currentWidth = entity!!.width
        var newWidth = currentWidth + GameConfig.PADDLE_EXPAND_SHRINK_SPEED * delta

        if (newWidth >= finalWidth) {
            newWidth = finalWidth
            finish()
        }

        entity!!.width = newWidth
    }

}
