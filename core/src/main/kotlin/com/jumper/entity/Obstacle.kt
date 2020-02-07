package com.jumper.entity

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Polygon
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.utils.Pool.Poolable
import com.jumper.config.GameConfig
import com.util.entity.EntityBase


class Obstacle : EntityBase(), Poolable {

    override var angleDeg = 0f
        set(value) {
            field = value % 360
            sensorAngleDeg = field + 20f
        }

    val sensor = Polygon()
    private var sensorAngleDeg = 0f
    private var radius = GameConfig.PLANET_HALF_SIZE - GameConfig.OBSTACLE_SIZE

    init {
        initSize()
    }

    override fun update(delta: Float) {
        if (radius < GameConfig.PLANET_HALF_SIZE) {
            // obstacle
            radius += delta
            val originX = GameConfig.WORLD_CENTER_X
            val originY = GameConfig.WORLD_CENTER_Y
            val newX = originX + MathUtils.cosDeg(-angleDeg) * radius
            val newY = originY + MathUtils.sinDeg(-angleDeg) * radius
            setPosition(newX, newY)
            // sensor
            val sensorX = originX + MathUtils.cosDeg(-sensorAngleDeg) * radius
            val sensorY = originY + MathUtils.sinDeg(-sensorAngleDeg) * radius
//            sensor.set(sensorX, sensorY, width, height)
            sensor.vertices = createVertices()
            sensor.setPosition(sensorX, sensorY)
        }
    }

    override fun reset() {
        radius = GameConfig.PLANET_HALF_SIZE - GameConfig.OBSTACLE_SIZE
        angleDeg = MathUtils.random(360f)
    }

    fun initSize() {
        setSize(GameConfig.OBSTACLE_SIZE, GameConfig.OBSTACLE_SIZE)
    }

}
