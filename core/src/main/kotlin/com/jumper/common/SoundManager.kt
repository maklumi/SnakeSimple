package com.jumper.common

import com.badlogic.gdx.assets.AssetManager
import com.jumper.assets.AssetDescriptors

class SoundManager(assetManager: AssetManager) {

    enum class SoundType {
        COIN, JUMP, LOSE;
    }

    private val coin = assetManager.get(AssetDescriptors.COIN)
    private val jump = assetManager.get(AssetDescriptors.JUMP)
    private val lose = assetManager.get(AssetDescriptors.LOSE)

    private val volume = 0.5f

    fun playSound(type: SoundType) {
        when (type) {
            SoundType.COIN -> coin.play(volume)
            SoundType.JUMP -> jump.play(volume)
            SoundType.LOSE -> lose.play(volume)
        }
    }
}