package com.brickbreaker.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.viewport.FitViewport
import com.brickbreaker.assets.AssetDescriptors
import com.brickbreaker.assets.RegionNames
import com.brickbreaker.common.ScoreController
import com.brickbreaker.config.GameConfig
import com.util.game.GameBase
import com.util.screen.transition.impl.FadeScreenTransition
import com.util.screen.transition.impl.ScaleScreenTransition
import com.util.screen.transition.impl.SlideScreenTransition
import ktx.app.clearScreen


class MenuScreen(private val game: GameBase) : ScreenAdapter() {

    private val assetManager = game.assetManager
    private val scoreController = ScoreController()

    private var viewport = FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT)
    private var stage = Stage(viewport, game.batch)

    override fun show() {
        val skin = assetManager.get(AssetDescriptors.SKIN)

        val table = Table()
        table.defaults().space(20f)
        table.background = TextureRegionDrawable(RegionNames.background())

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

        val highScoreString = "BEST: " + scoreController.highScore
        val highSScoreLabel = Label(highScoreString, skin)

        table.add(playButton).row()
        table.add(quitButton).row()
        table.add(highSScoreLabel)

        table.center()
        table.setFillParent(true)
        table.pack()

        stage.addActor(table)

        Gdx.input.inputProcessor = stage
    }

    override fun render(delta: Float) {
        clearScreen(0f, 0f, 0f)
        stage.act()
        stage.draw()
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
    }

    override fun hide() {
        dispose()
    }

    override fun dispose() {
        Gdx.input.inputProcessor = null
        stage.dispose()
    }

    private fun play() {
        when (MathUtils.random(1, 3)) {
            1 -> game.setScreen(GameScreen(game, scoreController), ScaleScreenTransition(2f))
            2 -> game.setScreen(GameScreen(game, scoreController), SlideScreenTransition(2f))
            3 -> game.setScreen(GameScreen(game, scoreController), FadeScreenTransition(2f))
        }
    }

    private fun quit() {
        Gdx.app.exit()
    }
}
