package com.util.entity.script

import com.util.entity.EntityBase


interface EntityScript<T : EntityBase> {

    var isFinished: Boolean

    fun added(entity: T)

    fun removed(removed: T)

    fun update(delta: Float)

}
