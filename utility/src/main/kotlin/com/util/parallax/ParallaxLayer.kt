package com.util.parallax

import com.badlogic.gdx.math.Rectangle
import com.util.Direction

class ParallaxLayer {

    var speed = 1f

    private var direction = Direction.LEFT
    private var startX = 0f
    private var startY = 0f
    private var width = 1f
    private var height = 1f

    val firstRegionBounds = Rectangle(startX, startY, width, height)
    val secondRegionBounds = Rectangle(startX + width, startY, width, height)


    fun update(delta: Float) {
        if (boundsReached(delta)) {
            resetBounds()
            return
        }

        val velocity = delta * speed

        when {
            direction.isLeft -> {
                firstRegionBounds.x -= velocity
                secondRegionBounds.x -= velocity
            }
            direction.isRight -> {
                firstRegionBounds.x += velocity
                secondRegionBounds.x += velocity
            }
            direction.isUp -> {
                firstRegionBounds.y += velocity
                secondRegionBounds.y += velocity
            }
            direction.isDown -> {
                firstRegionBounds.y -= velocity
                secondRegionBounds.y -= velocity
            }
        }
    }


    fun setStartPosition(startX: Float, startY: Float) {
        this.startX = startX
        this.startY = startY
        updateBounds()
    }

    fun setSize(width: Float, height: Float) {
        this.width = width
        this.height = height
        updateBounds()
    }

    fun setDirection(direction: Direction) {
        this.direction = direction
        updateBounds()
    }


    private fun updateBounds() {
        firstRegionBounds.set(startX, startY, width, height)

        when {
            direction.isRight -> secondRegionBounds.set(startX - width, startY, width, height)
            direction.isUp -> secondRegionBounds.set(startX, startY - height, width, height)
            direction.isDown -> secondRegionBounds.set(startX, startY + height, width, height)
            direction.isLeft -> secondRegionBounds.set(startX + width, startY, width, height)
        }
    }

    private fun boundsReached(delta: Float): Boolean {
        val velocity = speed * delta

        return when {
            direction.isRight -> {
                val nextPos = secondRegionBounds.x + velocity
                nextPos >= 0f
            }
            direction.isUp -> {
                val nextPos = secondRegionBounds.y + velocity
                nextPos >= 0f
            }
            direction.isDown -> {
                val nextPos = secondRegionBounds.y - velocity
                nextPos <= 0f
            }
            else -> { // left
                val nextPos = secondRegionBounds.x - velocity
                nextPos <= 0f
            }
        }

    }

    private fun resetBounds() {
        // assume left since left is default
        firstRegionBounds.setPosition(startX, startY)
        secondRegionBounds.setPosition(width, startY)

        when {
            direction.isRight -> secondRegionBounds.setPosition(-width, startY)
            direction.isUp -> secondRegionBounds.setPosition(startX, -height)
            direction.isDown -> secondRegionBounds.setPosition(startX, height)
        }
    }
}
