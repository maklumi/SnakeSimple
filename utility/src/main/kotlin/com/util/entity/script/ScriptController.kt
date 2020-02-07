package com.util.entity.script

import com.badlogic.gdx.utils.Array
import com.util.entity.EntityBase
import com.util.entity.script.EntityScript


class ScriptController(private val entity: EntityBase) {

    private val scripts = Array<EntityScript<EntityBase>>()

    fun update(delta: Float) {
        val iterator = Array.ArrayIterator(scripts)
        while (iterator.hasNext()) {
            val script = iterator.next()
            if (script.isFinished) {
                iterator.remove()
            } else {
                script.update(delta)
            }
        }
    }

    @Suppress("unchecked_cast")
    fun  addScript(entityScript: EntityScript<out EntityBase>) {
        scripts.add(entityScript as EntityScript<EntityBase>?)
        entityScript.added(entity)
    }

    fun removeScript(entityScript: EntityScript<EntityBase>) {
        scripts.removeValue(entityScript, true)
        entityScript.removed(entity)
    }
}
