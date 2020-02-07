package com.platformer.common

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.maps.MapObject
import com.badlogic.gdx.maps.objects.RectangleMapObject
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.utils.Logger
import com.platformer.assets.AssetDescriptors
import com.platformer.assets.LayerNames
import com.platformer.assets.MapObjectNames
import com.platformer.entity.Platform
import com.platformer.entity.WaterHazard
import com.platformer.screen.game.GameWorld
import com.util.entity.EntityBase
import com.util.map.MapUtils
import com.util.map.Validate


object EntityFactory {

    private val log = Logger(EntityFactory::class.java.simpleName, Logger.DEBUG)

    fun createGameWorld(assetManager: AssetManager): GameWorld {
        val world = GameWorld()

        val map = assetManager[AssetDescriptors.LEVEL_01]

        processLayer(map, LayerNames.HAZARDS, world)
        processLayer(map, LayerNames.PLATFORMS, world)

        return world
    }

    private fun processLayer(map: TiledMap, layerName: String, gameWorld: GameWorld) {
        val layers = map.layers
        val layer = layers[layerName]
        Validate.notNull(layer, "Layer with name $layerName not found")
        MapUtils.debugMapProperties(layer.properties)
        val mapObjects = layer.objects
        mapObjects.forEach {
            processMapObject(it, gameWorld)
            log.debug("mapObject=$it")
        }
    }

    private fun processMapObject(mapObject: MapObject, world: GameWorld) {
        when (mapObject.name) {
            MapObjectNames.HAZARD -> {
                val waterHazard = createWaterHazard(mapObject)
                world.waterHazards.add(waterHazard)
            }

            MapObjectNames.PLATFORMS -> {
                val platform = createPlatform(mapObject)
                world.platforms.add(platform)
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

    private fun createPlatform(mapObject: MapObject): Platform {
        val platform = Platform()
        initializeEntityObject(platform, mapObject)
        return platform
    }

    private fun <T : EntityBase> initializeEntityObject(entity: T, mapObject: MapObject) {
        val rectangle = (mapObject as RectangleMapObject).rectangle
        entity.apply {
            setPosition(rectangle.x, rectangle.y)
            setSize(rectangle.width, rectangle.height)
        }
    }
}
