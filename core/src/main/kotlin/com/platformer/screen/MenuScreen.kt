package com.platformer.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.utils.viewport.FitViewport
import com.platformer.SimplePlatformerGame
import com.platformer.assets.AssetDescriptors
import com.platformer.config.GameConfig
import com.util.GdxUtils


class MenuScreen(val game: SimplePlatformerGame) : ScreenAdapter() {

    private val viewport = FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT)
    private val stage = Stage(viewport, game.batch)
    private val skin = game.assetManager[AssetDescriptors.SKIN]

    override fun show() {
        val playButton = TextButton("PLAY", skin)
        playButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                play()
            }
        })

        val quitButton = TextButton("QUIT", skin)
        quitButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                quit()
            }
        })

        // create root table
        val table = Table(skin).apply {
            defaults().space(20f)
            setBackground("menu-background")
            add(playButton).row()
            add(quitButton).row()
            center()
            setFillParent(true)
            pack()
        }

        stage.addActor(table)
        Gdx.input.inputProcessor = stage
    }

    override fun render(delta: Float) {
        viewport.apply()

        GdxUtils.clearScreen()
        stage.act()
        stage.draw()
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height)
    }

    override fun hide() {
        dispose()
    }

    override fun dispose() {
        Gdx.input.inputProcessor = null
        stage.dispose()
    }

    private fun play() {
        game.screen = GameScreen(game)
    }

    private fun quit() {
        Gdx.app.exit()
    }
}