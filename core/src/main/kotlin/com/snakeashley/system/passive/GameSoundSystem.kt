package com.snakeashley.system.passive

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.assets.AssetManager
import com.snakesimple.assets.Descriptor

class GameSoundSystem(assetManager: AssetManager) : EntitySystem() {

    private val coin = assetManager[Descriptor.COIN_SOUND]
    private val lose = assetManager[Descriptor.LOSE_SOUND]
    private val volume = 0.5f

    fun hitCoin() {
        coin.play(volume)
    }

    fun lose() {
        lose.play(volume)
    }

    override fun checkProcessing(): Boolean {
        return false
    }
}