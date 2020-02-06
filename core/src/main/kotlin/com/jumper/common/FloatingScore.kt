package com.jumper.common

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.utils.Pool
import com.jumper.config.GameConfig


class FloatingScore : Pool.Poolable {

    var color = Color.WHITE.cpy()
    var score = 0
    private var startX = 0f
    private var startY = 0f
    var x = 0f
    var y = 0f
    private var timer = 0f

    fun update(delta: Float) {
        if (isFinished()) {
            return
        }
        timer += delta
        val percentage: Float = timer / GameConfig.FLOATING_DURATION
        val alpha = MathUtils.clamp(1.0f - percentage, 0f, 1f)
        x = startX
        y = startY + percentage * 60
        color.a = alpha
    }

    fun isFinished(): Boolean {
        return timer >= GameConfig.FLOATING_DURATION
    }

    fun setStartPosition(startX: Float, startY: Float) {
        this.startX = startX
        this.startY = startY
    }

    override fun reset() {
        score = 0
        startX = 0f
        startY = 0f
        timer = 0f
        x = 0f
        y = 0f
        color.a = 1.0f
    }
}