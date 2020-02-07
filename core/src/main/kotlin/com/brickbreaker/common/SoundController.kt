package com.brickbreaker.common

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Sound
import com.brickbreaker.assets.AssetDescriptors


class SoundController(assetManager: AssetManager) {

    private val hit: Sound = assetManager.get(AssetDescriptors.HIT)
    private val lost: Sound = assetManager.get(AssetDescriptors.LOST)
    private val pickup: Sound = assetManager.get(AssetDescriptors.PICKUP)

    private val volume = 0.2f

    fun hit() {
        hit.play(volume)
    }

    fun pickup() {
        pickup.play(volume)
    }

    fun lost() {
        lost.play(volume)
    }

}
