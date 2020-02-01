package com.snakesimple.entity

import com.badlogic.gdx.utils.Array
import com.snakesimple.config.GameConfig

class Snake {

    val head = SnakeHead()
    val bodyParts = Array<BodyPart>()

    var direction = Direction.UP
    private var prevX = 0f
    private var prevY = 0f

    fun move() {
        prevX = head.x
        prevY = head.y
        updateBodyPartPosition()

        when (direction) {
            Direction.LEFT -> head.x -= GameConfig.SNAKE_SPEED
            Direction.RIGHT -> head.x += GameConfig.SNAKE_SPEED
            Direction.UP -> head.y += GameConfig.SNAKE_SPEED
            Direction.DOWN -> head.y -= GameConfig.SNAKE_SPEED
        }
    }

    fun insertBodyPart() {
        val bodyPart = BodyPart()
        // put in same head position
        bodyPart.setPosition(head.x, head.y)
        // put at tail, index = 0
        bodyParts.insert(0, bodyPart)
    }

    private fun updateBodyPartPosition() {
        if (!bodyParts.isEmpty) {
            val tailPart = bodyParts.removeIndex(0) // remove
            tailPart.setPosition(prevX, prevY)      // update
            bodyParts.add(tailPart)                 // put back
            bodyParts.first()
        }
    }

}