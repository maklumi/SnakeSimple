package com.platformer.entity

import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.platformer.assets.RegionNames
import com.platformer.config.GameConfig
import com.util.entity.EntityBase


class Player : EntityBase() {

    //equivalent gamePlayAtlas.findRegion("player-jumping")
    var region: TextureAtlas.AtlasRegion = RegionNames.player_jumping()

    var state = PlayerState.FALLING

    override fun update(delta: Float) {
        super.update(delta)

        velocity.y += GameConfig.GRAVITY_Y * delta
        setVelocityX(x + velocity.x * delta)
    }

    fun jump() {
        state = PlayerState.JUMPING
        velocity.y = GameConfig.JUMP_VELOCITY
    }
}