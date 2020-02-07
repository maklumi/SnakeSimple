package com.platformer.screen

import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.platformer.SimplePlatformerGame
import com.platformer.assets.AssetDescriptors
import com.platformer.config.GameConfig
import com.platformer.screen.game.GameController
import com.platformer.screen.game.GameRenderer
import com.platformer.screen.game.GameWorld
import com.util.debug.DebugCameraController


class GameScreen(val game: SimplePlatformerGame) : ScreenAdapter() {

    private val gameWorld = GameWorld()
    private val renderer = GameRenderer(gameWorld, game.batch, game.assetManager)
    private val controller = GameController(gameWorld, renderer)

    override fun show() {
        DebugCameraController.setStartPosition(GameConfig.WORLD_CENTER_X, GameConfig.WORLD_CENTER_Y)
    }

    override fun render(delta: Float) {
        controller.update(delta)
        renderer.update(delta)
    }

    override fun resize(width: Int, height: Int) {
        renderer.resize(width, height)
    }

    override fun hide() {
        dispose()
    }

    override fun dispose() {
        renderer.dispose()
    }
}