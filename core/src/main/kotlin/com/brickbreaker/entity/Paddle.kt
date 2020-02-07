package com.brickbreaker.entity

import com.badlogic.gdx.math.MathUtils
import com.brickbreaker.config.GameConfig
import com.brickbreaker.entity.script.ScriptController

class Paddle : EntityBase() {

    val scriptController = ScriptController(this)

    override fun update(delta: Float) {
        super.update(delta)
        scriptController.update(delta)
    }

    fun limitX() {
        x = MathUtils.clamp(x, 0f, GameConfig.WORLD_WIDTH - width)
    }
}