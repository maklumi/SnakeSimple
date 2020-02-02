package com.snakeashley.screen

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.viewport.FitViewport
import com.snakeashley.common.EntityFactory
import com.snakeashley.system.debug.DebugCameraSystem
import com.snakeashley.system.debug.DebugRenderSystem
import com.snakeashley.system.debug.GridRenderSystem
import com.snakesimple.SimpleSnakeMain
import com.snakesimple.common.GameManager
import com.snakesimple.common.GameState
import com.snakesimple.config.GameConfig
import com.snakesimple.screen.MenuScreen
import com.snakesimple.util.ViewportUtils
import ktx.app.clearScreen

class GameScreen(private val game: SimpleSnakeMain) : ScreenAdapter() {

    private val assetManager = game.assetManager
    private val camera = OrthographicCamera()
    private val viewport = FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera)
    private var renderer = ShapeRenderer()

    private val engine = PooledEngine()
    private val factory = EntityFactory(engine)

    override fun show() {

        val debugSystems = arrayListOf(
                GridRenderSystem(viewport, renderer)
                , DebugCameraSystem(camera)
                , DebugRenderSystem(renderer, viewport)
        )
        debugSystems.forEach { engine.addSystem(it) }

        factory.createSnakeHead()
    }

    override fun render(delta: Float) {
        clearScreen(0f, 0f, 0f)

        engine.update(delta)

        if (GameManager.isGameOver()) {
            GameManager.state = GameState.READY // ready for next round
            game.screen = MenuScreen(game)
        }

    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height)
        ViewportUtils.debugPixelPerUnit(viewport)
    }

    override fun hide() {
        dispose()
    }

    override fun dispose() {
        renderer.dispose()
    }
}