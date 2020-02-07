package com.platformer.common

import com.platformer.config.GameConfig.WORLD_WIDTH
import com.platformer.entity.WaterHazard


object EntityFactory {

     fun createWaterHazard(): WaterHazard {
        return WaterHazard().apply {
            setPosition(0f, 0f)
            setSize(WORLD_WIDTH, 1.0f)

        }
    }

}
