package com.snakesimple.screen

import com.badlogic.gdx.ScreenAdapter
import com.snakesimple.SimpleSnakeMain
import com.snakesimple.assets.Descriptor
import com.snakesimple.collision.CollisionListener
import com.snakesimple.common.GameManager
import com.snakesimple.common.GameState

class GameScreen(private val game: SimpleSnakeMain) : ScreenAdapter() {

    private val assetManager = game.assetManager
    private val coinSound = assetManager.get(Descriptor.COIN_SOUND)
    private val loseSound = assetManager.get(Descriptor.LOSE_SOUND)
    private val collisionListener = object : CollisionListener() {
        override fun hitCoin() {
            coinSound.play()
        }

        override fun lose() {
            loseSound.play()
        }
    }

    private val controller = GameController(collisionListener)
    private val renderer = GameRenderer(game.batch, controller, assetManager)

    override fun show() {

    }

    override fun render(delta: Float) {
        controller.update(delta)
        renderer.render(delta)

        if (GameManager.isGameOver()) {
            GameManager.state = GameState.READY // ready for next round
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