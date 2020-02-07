package com.util.map

import com.badlogic.gdx.maps.MapProperties
import com.badlogic.gdx.utils.Logger

object MapUtils {

    private val log = Logger(MapUtils::class.java.simpleName, Logger.DEBUG)
    private val LS = System.getProperty("line.separator")
    private const val TAB = "\t"


    fun debugMapProperties(mapProperties: MapProperties) {
        val sb = StringBuffer()
        sb.append("properties").append(LS)

        val keyIterator = mapProperties.keys
        while (keyIterator.hasNext()) {
            val key = keyIterator.next()
            val value = mapProperties.get(key)

            val typeName = value.javaClass.simpleName
            val keyValueString = "key= $key value= $value type= $typeName"

            sb.append(TAB)
            sb.append(keyValueString)

            if (keyIterator.hasNext()) {
                sb.append(LS)
            }
        }

        log.debug(sb.toString())
    }
}
