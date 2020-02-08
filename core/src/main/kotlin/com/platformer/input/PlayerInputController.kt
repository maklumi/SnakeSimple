package com.platformer.input

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.platformer.config.GameConfig
import com.platformer.screen.game.GameWorld


class PlayerInputController(private val world: GameWorld) {

    fun update() {
        var velocityX = 0f

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            velocityX = -GameConfig.MOVE_VELOCITY
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            velocityX = GameConfig.MOVE_VELOCITY
        }
        // set player velocity
        world.player.velocity.x = velocityX
    }
}
