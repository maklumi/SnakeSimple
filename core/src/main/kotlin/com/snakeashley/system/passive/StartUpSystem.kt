package com.snakeashley.system.passive

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem


class StartUpSystem : EntitySystem() {

    private lateinit var factory: EntityFactorySystem

    lateinit var snake: Entity

    override fun checkProcessing(): Boolean {
        // not processing. no update() also
        return false
    }

    override fun addedToEngine(engine: Engine) {
        factory = engine.getSystem(EntityFactorySystem::class.java)
        startUp()
    }

    private fun startUp() {
        snake = factory.createSnake()
        factory.createBackground()
        factory.createCoin()
    }
}