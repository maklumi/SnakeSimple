package com.jumper.screen.menu

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.jumper.assets.ButtonStyleNames
import com.jumper.assets.RegionNames
import com.jumper.common.GameManager
import ktx.actors.onChange


class MenuOverlay(skin: Skin, private val callback: OverlayCallback) : Table(skin) {

    private val highScoreLabel: Label

    init {
        defaults().pad(20f)

        val logoTable = Table()
        logoTable.top()
        val logoImage = Image(skin, RegionNames.LOGO)
        logoTable.add(logoImage)

        val buttonTable = Table()
        val playButton = ImageButton(skin, ButtonStyleNames.PLAY)
        playButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                callback.ready()
            }
        })

        val quitButton = ImageButton(skin, ButtonStyleNames.QUIT)
        quitButton.onChange {
            Gdx.app.exit()
        }

        val scoreTable = Table(skin)
        scoreTable.add("BEST: ").row()
        highScoreLabel = Label("", skin)
        updateLabel()
        scoreTable.add(highScoreLabel)

        buttonTable.add(playButton).left().expandX()
        buttonTable.add(scoreTable).center().expandX()
        buttonTable.add(quitButton).right().expandX()

        add(logoTable).top().grow().row()
        add(buttonTable).grow().center().row()
        center()
        setFillParent(true)
        pack()
    }

    fun updateLabel() {
        highScoreLabel.setText("" + GameManager.displayHighScore)
    }
}