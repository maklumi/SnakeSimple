package com.util.entity.script

import com.util.entity.EntityBase


abstract class EntityScriptBase<T : EntityBase> : EntityScript<T> {

    var entity: T? = null

    override var isFinished: Boolean = false

    override fun added(entity: T) {
        this.entity = entity
    }

    override fun removed(removed: T) {
        this.entity = null
    }

    fun finish() {
        isFinished = true
    }
}
