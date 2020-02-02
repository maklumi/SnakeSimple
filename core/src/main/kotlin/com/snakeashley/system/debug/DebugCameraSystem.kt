package com.snakeashley.system.debug

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.graphics.OrthographicCamera
import com.snakesimple.config.GameConfig
import com.snakesimple.util.debug.DebugCameraController


class DebugCameraSystem(private val camera: OrthographicCamera) : EntitySystem() {

    init {
        DebugCameraController.setStartPosition(GameConfig.WORLD_CENTER_X, GameConfig.WORLD_CENTER_Y)
    }

    override fun update(deltaTime: Float) {
        DebugCameraController.handleDebugInput(deltaTime)
        DebugCameraController.applyTo(camera)
    }

}