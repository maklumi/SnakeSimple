package com.platformer.level

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.Logger
import com.platformer.assets.AssetDescriptors


object LevelController {

    private val log = Logger(LevelController::class.java.simpleName, Logger.DEBUG)

    private const val LEVEL_PATH = "assets-platformer/level"
    private const val DOT_TMX = ".tmx"
    private val LEVEL_DIR: FileHandle = Gdx.files.internal(LEVEL_PATH)

    private val levelDescriptors = Array<AssetDescriptor<TiledMap>>().apply {
        val levelFileHandles = LEVEL_DIR.list(DOT_TMX)
        for (fileHandle in levelFileHandles) {
            log.debug("path= ${fileHandle.path()}")
            val descriptor = AssetDescriptor(fileHandle.path(), TiledMap::class.java, AssetDescriptors.MAP_PARAMS)
            add(descriptor)
        }
        log.debug("levelDescriptors-size $size")
    }

    private var currentLevel: AssetDescriptor<TiledMap>? = null

    lateinit var assetManager: AssetManager

    val tiledMap: TiledMap
        get() = assetManager[currentLevel]

    fun loadRandomLevel() {
        currentLevel = levelDescriptors.random()
//        assetManager.load(currentLevel)
//        assetManager.finishLoading()
        log.debug("currentLevel $currentLevel")
    }

}
