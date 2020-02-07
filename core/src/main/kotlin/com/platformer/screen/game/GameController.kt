package com.platformer.screen.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2


class GameController(val game: GameWorld, val renderer: GameRenderer) {

    fun update(delta: Float) {
        handleDebugInput()
        game.update(delta)
    }

    fun screenToWorld(screenCoordinates: Vector2) = renderer.screenToWorld(screenCoordinates)

    private fun handleDebugInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.F5)) game.toggleDrawGrid()
        if (Gdx.input.isKeyJustPressed(Input.Keys.F6)) game.toggleDrawDebug()
        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) Gdx.app.exit()
    }

}