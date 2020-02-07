package com.brickbreaker.entity.script

import com.brickbreaker.entity.EntityBase

interface EntityScript<T : EntityBase> {

    var isFinished: Boolean

    fun added(entity: T)

    fun removed(removed: T)

    fun update(delta: Float)

}
