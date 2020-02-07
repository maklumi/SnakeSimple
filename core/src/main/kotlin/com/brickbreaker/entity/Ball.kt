package com.brickbreaker.entity

import com.badlogic.gdx.math.Circle
import com.brickbreaker.entity.script.ScriptController

class Ball : EntityBase() {

    override val scriptController = ScriptController(this)

    val bound = Circle()

}