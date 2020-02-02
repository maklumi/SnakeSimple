package com.snakeashley.screen

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.Logger
import com.badlogic.gdx.utils.viewport.FitViewport
import com.snakeashley.common.EntityFactory
import com.snakeashley.system.debug.DebugCameraSystem
import com.snakeashley.system.debug.DebugRenderSystem
import com.snakeashley.system.debug.GridRenderSystem
import com.snakeashley.system.passive.SnakePassiveSystem
import com.snakesimple.SimpleSnakeMain
import com.snakesimple.common.GameManager
import com.snakesimple.common.GameState
import com.snakesimple.config.GameConfig
import com.snakesimple.screen.MenuScreen
import com.snakesimple.util.ViewportUtils
import ktx.app.clearScreen

class GameScreen(private val game: SimpleSnakeMain) : ScreenAdapter() {

    private val log = Logger(GameScreen::class.java.simpleName, Logger.DEBUG)
    private val assetManager = game.assetManager
    private val camera = OrthographicCamera()
    private val viewport = FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera)
    private var renderer = ShapeRenderer()

    private val engine = PooledEngine()
    private val factory = EntityFactory(engine)
    private lateinit var snake: Entity

    override fun show() {

        val debugSystems = arrayListOf(
                GridRenderSystem(viewport, renderer)
                , DebugCameraSystem(camera)
                , DebugRenderSystem(renderer, viewport)
        )
        debugSystems.forEach { engine.addSystem(it) }

        val systems = arrayListOf<EntitySystem>(
                SnakePassiveSystem()
        )
        systems.forEach { engine.addSystem(it) }

        snake = factory.createSnake()
    }

    override fun render(delta: Float) {
        clearScreen(0f, 0f, 0f)

        engine.update(delta)

        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            log.debug("before removal, entity=${engine.entities.size()}")
            engine.removeEntity(snake)
            log.debug("after removal, entity=${engine.entities.size()}")
        }

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