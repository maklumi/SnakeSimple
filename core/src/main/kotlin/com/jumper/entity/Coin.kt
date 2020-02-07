package com.jumper.entity

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.utils.Pool.Poolable
import com.jumper.config.GameConfig
import com.util.entity.EntityBase


class Coin : EntityBase(), Poolable {

    override var angleDeg = 0f
        set(value) {
            field = value % 360
            var radius: Float = GameConfig.PLANET_HALF_SIZE
            if (offset) {
                radius += GameConfig.COIN_SIZE
            }
            val originX: Float = GameConfig.WORLD_CENTER_X
            val originY: Float = GameConfig.WORLD_CENTER_Y
            val newX = originX + MathUtils.cosDeg(-angleDeg) * radius
            val newY = originY + MathUtils.sinDeg(-angleDeg) * radius
            setPosition(newX, newY)
        }

    var offset = false
    var scale = 0f

    private val scaleMax = 1.0f

    init {
        initSize()
    }

    override fun update(delta: Float) {
        if (scale < scaleMax) {
            scale += delta
        }
    }

    override fun reset() {
        offset = false
        scale = 0.0f
        angleDeg = MathUtils.random(360f)
    }

    fun initSize() {
        setSize(GameConfig.COIN_SIZE, GameConfig.COIN_SIZE)
    }

}