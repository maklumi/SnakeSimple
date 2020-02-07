package com.brickbreaker.entity

import com.util.entity.script.ScriptController
import com.util.entity.EntityBase
import com.util.shape.ShapeUtils

class Ball : EntityBase() {

    override val scriptController = ScriptController(this)

    override fun createVertices(): FloatArray {
        return ShapeUtils.createOctagon(
                width / 2f,     // origin x or center r
                height / 2f,    // origin y or center y
                width / 2f      // radius
        )
    }
}