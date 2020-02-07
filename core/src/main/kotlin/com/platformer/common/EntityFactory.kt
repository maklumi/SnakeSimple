package com.platformer.common

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.maps.MapObject
import com.badlogic.gdx.maps.objects.RectangleMapObject
import com.badlogic.gdx.utils.Logger
import com.platformer.assets.AssetDescriptors
import com.platformer.assets.LayerNames.HAZARDS
import com.platformer.assets.MapObjectNames
import com.platformer.entity.WaterHazard
import com.platformer.screen.game.GameWorld
import com.util.map.MapUtils
import com.util.map.Validate


object EntityFactory {

    private val log = Logger(EntityFactory::class.java.simpleName, Logger.DEBUG)
    
    fun createGameWorld(assetManager: AssetManager): GameWorld {
        val world = GameWorld()

        val map = assetManager[AssetDescriptors.LEVEL_01]
        MapUtils.debugMapProperties(map.properties)

        val hazardsLayer = map.layers[HAZARDS]
        Validate.notNull(hazardsLayer, "Layer with name $HAZARDS not found")
        MapUtils.debugMapProperties(hazardsLayer.properties)

        val mapObjects = hazardsLayer.objects
        val iterator = mapObjects.iterator()
        while (iterator.hasNext()) {
            val mapObject = iterator.next()
            processMapObject(mapObject, world)
            log.debug("mapObject= $mapObject")
            MapUtils.debugMapProperties(mapObject.properties)

            if (mapObject is RectangleMapObject) {
                log.debug("rectangle= ${mapObject.rectangle}")
            }
        }

        return world
    }

    private fun processMapObject(mapObject: MapObject, world: GameWorld) {
        when (mapObject.name) {
            MapObjectNames.HAZARD -> {
                val waterHazard = createWaterHazard(mapObject)
                world.waterHazards.add(waterHazard)
            }

        }
    }

    private fun createWaterHazard(mapObject: MapObject): WaterHazard {
        if (mapObject !is RectangleMapObject)
            throw IllegalArgumentException("Water hazard needs to be represented by rectangle")

        val rect = mapObject.rectangle

        return WaterHazard().apply {
            setPosition(rect.x, rect.y)
            setSize(rect.width, rect.height)
        }
    }

}
