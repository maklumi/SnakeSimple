package com.snakeashley.util

import com.badlogic.ashley.core.Entity
import com.snakeashley.common.Z_ORDER
import ktx.ashley.get


object ZOrderComparator : Comparator<Entity> {

    override fun compare(first: Entity, second: Entity): Int {
        return first[Z_ORDER]!!.z.compareTo(second[Z_ORDER]!!.z)
    }

}