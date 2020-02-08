package com.platformer.screen

import com.badlogic.gdx.ScreenAdapter
import com.platformer.SimplePlatformerGame
import com.platformer.common.EntityFactory
import com.platformer.common.GameState
import com.platformer.config.GameConfig
import com.platformer.input.PlayerInputController
import com.platformer.screen.game.GameController
import com.platformer.screen.game.GameRenderer
import com.util.debug.DebugCameraController
import com.util.screen.transition.impl.FadeScreenTransition


class GameScreen(val game: SimplePlatformerGame) : ScreenAdapter() {

    private val gameWorld = EntityFactory.createGameWorld(game.assetManager)
    private val renderer = GameRenderer(gameWorld, game.batch, game.assetManager)
    private val controller = GameController(gameWorld, renderer)
    private val playerInputController = PlayerInputController(gameWorld)

    private var timer = 0f

    override fun show() {
        DebugCameraController.setStartPosition(GameConfig.WORLD_CENTER_X, GameConfig.WORLD_CENTER_Y)
    }

    override fun render(delta: Float) {
        if (gameWorld.state == GameState.PLAYING) {
            playerInputController.update()
            controller.update(delta)
        }
        renderer.update(delta)

        if (gameWorld.state == GameState.LEVEL_COMPLETE) {
            timer += delta
            if (timer > 1f)
                timer = 0f
            game.setScreen(GameScreen(game), FadeScreenTransition(1f))
        }

        if (gameWorld.isGameOver) {
            game.screen = MenuScreen(game)
        }
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