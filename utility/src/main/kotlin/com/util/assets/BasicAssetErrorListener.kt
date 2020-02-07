package com.util.assets

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetErrorListener
import com.badlogic.gdx.utils.Logger

object BasicAssetErrorListener : AssetErrorListener {

    private val log = Logger(BasicAssetErrorListener::class.java.simpleName, Logger.DEBUG)

    override fun error(asset: AssetDescriptor<*>, throwable: Throwable) {
        val message = "Error loading asset=${asset.file} path=${asset.file.path()} type=${asset.type}"
        log.error(message, throwable)
    }

}

