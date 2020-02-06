package com.jumper.screen.menu

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.jumper.assets.ButtonStyleNames
import com.jumper.assets.RegionNames
import com.jumper.common.GameManager


class GameOverOverlay(skin: Skin, private val callback: OverlayCallback) : Table(skin) {

    private val scoreLabel: Label
    private val highScoreLabel: Label

    init {
        defaults().pad(20f)

        val gameOverImage = Image(skin, RegionNames.GAME_OVER)

        // score table
        val scoreTable = Table(skin)
        scoreTable.defaults().pad(10f)
        scoreTable.setBackground(RegionNames.PANEL)

        scoreTable.add("SCORE: ").row()
        scoreLabel = Label("", skin)
        scoreTable.add(scoreLabel).row()

        scoreTable.add("BEST: ").row()
        highScoreLabel = Label("", skin)
        scoreTable.add(highScoreLabel)

        scoreTable.center()

        // button table
        val buttonTable = Table()

        val homeButton = ImageButton(skin, ButtonStyleNames.HOME)
        homeButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                callback.home()
            }
        })

        val restartButton = ImageButton(skin, ButtonStyleNames.RESTART)
        restartButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                callback.ready()
            }
        })

        buttonTable.add(homeButton).left().expandX()
        buttonTable.add(restartButton).right().expandX()

        add(gameOverImage).row()
        add(scoreTable).row()
        add(buttonTable).grow().center()

        center()
        setFillParent(true)
        pack()

        updateLabels()
    }

    fun updateLabels() {
        scoreLabel.setText("${GameManager.score}")
        highScoreLabel.setText("${GameManager.displayHighScore}")
    }
}