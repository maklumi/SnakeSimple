package com.snakeashley.system.debug

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input

/*
Should be added after adding GridRenderSystem and DebugRenderSystem to engine
 */
class DebugInputSystem : EntitySystem() {

    private var debugGrid = true
    private var debugRender = true
    private lateinit var gridRenderSystem: EntitySystem
    private lateinit var debugRenderSystem: EntitySystem

    override fun addedToEngine(engine: Engine) {
        gridRenderSystem = engine.getSystem(GridRenderSystem::class.java)
        debugRenderSystem = engine.getSystem(DebugRenderSystem::class.java) // always should call last
        toggleSystem()
    }

    override fun update(deltaTime: Float) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.F5)) {
            debugGrid = !debugGrid
            toggleSystem()
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.F6)) {
            debugRender = !debugRender
            toggleSystem()
        }
    }

    private fun toggleSystem() {
        gridRenderSystem.setProcessing(debugGrid)
        debugRenderSystem.setProcessing(debugRender)
    }
}