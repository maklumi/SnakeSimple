package com.brickbreaker.entity

import com.badlogic.gdx.math.MathUtils
import com.brickbreaker.config.GameConfig
import com.util.entity.script.ScriptController
import com.util.entity.EntityBase

class Paddle : EntityBase() {

    override val scriptController = ScriptController(this)

    fun limitX() {
        x = MathUtils.clamp(x, 0f, GameConfig.WORLD_WIDTH - width)
    }
}