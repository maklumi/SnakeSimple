package com.platformer.common

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.maps.MapObject
import com.badlogic.gdx.maps.objects.RectangleMapObject
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.utils.Logger
import com.platformer.assets.AssetDescriptors
import com.platformer.assets.LayerNames
import com.platformer.assets.MapObjectNames
import com.platformer.config.GameConfig
import com.platformer.entity.Coin
import com.platformer.entity.Platform
import com.platformer.entity.Player
import com.platformer.entity.WaterHazard
import com.platformer.screen.game.GameWorld
import com.util.entity.EntityBase
import com.util.map.MapUtils
import com.util.map.Validate


object EntityFactory {

    private val log = Logger(EntityFactory::class.java.simpleName, Logger.DEBUG)
    private var debug = false

    fun createGameWorld(assetManager: AssetManager): GameWorld {
        val world = GameWorld()

        val map = assetManager[AssetDescriptors.LEVEL_01]

        processLayer(map, LayerNames.HAZARDS, world)
        processLayer(map, LayerNames.PLATFORMS, world)
        processLayer(map, LayerNames.PLAYER, world)
        processLayer(map, LayerNames.COLLECTIBLES, world)

        return world
    }

    private fun processLayer(map: TiledMap, layerName: String, gameWorld: GameWorld) {
        val layers = map.layers
        val layer = layers[layerName]
        Validate.notNull(layer, "Layer with name $layerName not found")
        if (debug) MapUtils.debugMapProperties(layer.properties)
        val mapObjects = layer.objects
        mapObjects.forEach {
            processMapObject(it, gameWorld)
            if (debug) log.debug("mapObject=$it")
        }
    }

    private fun processMapObject(mapObject: MapObject, world: GameWorld) {
        when (mapObject.name) {
            MapObjectNames.HAZARD -> {
                val waterHazard = initializeEntityObject(WaterHazard(), mapObject)
                world.waterHazards.add(waterHazard)
            }

            MapObjectNames.PLATFORMS -> {
                val platform = initializeEntityObject(Platform(), mapObject)
                world.platforms.add(platform)
            }
            MapObjectNames.PLAYER -> {
                val player = initializeEntityObject(Player(), mapObject)
                player.setSize(GameConfig.PLAYER_SIZE)
                player.saveStartPosition(player.x, player.y)
                world.player = player
            }
            MapObjectNames.COIN -> {
                val coin = initializeEntityObject(Coin(), mapObject)
                coin.setSize(GameConfig.COIN_SIZE)
                world.coins.add(coin)
            }
        }
    }


    private fun <T : EntityBase> initializeEntityObject(entity: T, mapObject: MapObject): T {
        val rectangle = (mapObject as RectangleMapObject).rectangle
        entity.apply {
            setPosition(rectangle.x, rectangle.y)
            setSize(rectangle.width, rectangle.height)
        }
        return entity
    }
}
