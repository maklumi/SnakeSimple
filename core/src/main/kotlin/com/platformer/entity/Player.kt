package com.platformer.entity

import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.platformer.assets.RegionNames
import com.platformer.config.GameConfig
import com.util.entity.EntityBase


class Player : EntityBase() {

    private val jump = RegionNames.player_jumping()
    private val fall = RegionNames.player_falling()

    val region: TextureAtlas.AtlasRegion
        get() = if (isFalling()) fall else jump

    private val isFacingRight: Boolean
        get() = velocity.x > 0.01f

    private val isFacingLeft: Boolean
        get() = velocity.x < -0.01f

    private var state = PlayerState.FALLING

    override fun update(delta: Float) {
        super.update(delta)

        velocity.y += GameConfig.GRAVITY_Y * delta
        setVelocityX(velocity.x * delta)

        if (velocity.y < 0) fall()

        val isFlipX = region.isFlipX
        if (isFacingRight && isFlipX) {
            region.flip(true, false)
        }
        if (isFacingLeft && !isFlipX) {
            region.flip(true, false)
        }
    }

    fun jump() {
        state = PlayerState.JUMPING
        velocity.y = GameConfig.JUMP_VELOCITY
    }

    private fun fall() {
        state = PlayerState.FALLING
    }

    fun die() {
        state = PlayerState.DEAD
    }

    fun isFalling(): Boolean = state.isFalling
}