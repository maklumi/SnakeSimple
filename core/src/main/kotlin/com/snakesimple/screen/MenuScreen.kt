package com.snakesimple.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.viewport.FitViewport
import com.snakesimple.SimpleSnakeMain
import com.snakesimple.assets.ButtonStyleNames
import com.snakesimple.assets.Descriptor
import com.snakesimple.assets.RegionNames
import com.snakesimple.config.GameConfig
import ktx.app.clearScreen

class MenuScreen(private val game: SimpleSnakeMain) : ScreenAdapter() {

    private val assetManager = game.assetManager
    private val skin = assetManager.get(Descriptor.UI_SKIN)
    private val gamePlayAtlas = assetManager.get(Descriptor.GAME_PLAY)

    private val viewport = FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT)
    private lateinit var stage: Stage

    override fun show() {
        stage = Stage(viewport, game.batch)
        Gdx.input.inputProcessor = stage
        stage.addActor(createUi())
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
        stage.dispose()
    }

    private fun createUi(): Actor {
        val table = Table(skin)
        table.defaults().pad(10f)

        val bgRegion = gamePlayAtlas.findRegion(RegionNames.BACKGROUND)
        table.background = TextureRegionDrawable(bgRegion)

        val title = Image(skin, RegionNames.TITLE)

        val playButton = Button(skin, ButtonStyleNames.PLAY)
        playButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                game.screen = com.snakeashley.screen.GameScreen(game)
            }
        })

        val quitButton = Button(skin, ButtonStyleNames.QUIT)
        quitButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                Gdx.app.exit()
            }
        })

        table.add(title).row()
        table.add(playButton).row()
        table.add(quitButton)

        table.center()
        table.setFillParent(true)
        table.pack()
        return table
    }
}