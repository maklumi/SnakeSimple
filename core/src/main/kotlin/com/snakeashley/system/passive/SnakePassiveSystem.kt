package com.snakeashley.system.passive

import com.badlogic.ashley.core.*
import com.badlogic.gdx.utils.Logger
import com.snakeashley.SnakeComponent
import com.snakeashley.common.SNAKE_COMPONENT
import ktx.ashley.get

class SnakePassiveSystem : EntitySystem(), EntityListener {

    private val log = Logger(javaClass.simpleName, Logger.DEBUG)

    private val family = Family.all(SnakeComponent::class.java).get()

    // must return false
    override fun checkProcessing(): Boolean {
        return false // false = make this system passive. update() will not be called.
    }

    override fun addedToEngine(engine: Engine) {
        log.debug("SnakeSystem added to engine and adding listener")
        engine.addEntityListener(family, this)
    }

    override fun removedFromEngine(engine: Engine) {
        log.debug("SnakeSystem removedFromEngine")
        engine.removeEntityListener(this)
    }

    // implement EntityListener functions

    override fun entityAdded(entity: Entity) {
    }

    override fun entityRemoved(entity: Entity) {
        log.debug("SnakeSystem entityRemoved $entity")
        val snake = entity[SNAKE_COMPONENT]!!
        // avoid dangling entity, so we remove linked entity one by one
        engine.removeEntity(snake.head)

        snake.bodyParts.forEach { engine.removeEntity(it) }
    }
}