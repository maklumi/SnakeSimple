package com.platformer.entity

import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.platformer.assets.RegionNames
import com.util.entity.EntityBase

class Coin : EntityBase() {

    var region: TextureAtlas.AtlasRegion = RegionNames.coin()

}