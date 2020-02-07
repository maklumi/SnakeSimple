package com.brickbreaker.entity

import com.badlogic.gdx.utils.Pool
import com.util.entity.EntityBase

class Pickup : EntityBase(), Pool.Poolable {

    var type: PickupType = PickupType.random()

    val isExpand: Boolean
        get() = type.isExpand

    val isShrink: Boolean
        get() = type.isShrink

    val isSlowDown: Boolean
        get() = type.isSlowDown

    val isSpeedUp: Boolean
        get() = type.isSpeedUp

    override fun reset() {
        velocity.setZero()
    }
}
