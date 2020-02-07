@file:Suppress("UnusedMainParameter")

package com.brickbreaker

/*
exception
Caused by: java.lang.SecurityException: sealing violation: package org.lwjgl is sealed
download particle editor jar to run
 */
import com.badlogic.gdx.tools.particleeditor.ParticleEditor

object ParticleEffectsCreator {

    @JvmStatic
    fun main(args: Array<String>) {
        ParticleEditor.main(null)
    }
}

/**
 * Emitter properties to create fire. Eg below
 * Image - choose fire.png
 * Save as fire.pfx
 * Count - Min 0 Max 200
 * Duration 500 ms
 * Emission Hi 125
 * Life Hi 500-1000 ms
 * Spawn shape point
 * Size Hi 0.3 world unit, Lo 0.15, Set end to 60%
 * Velocity Hi 1-3
 * Angle Hi 180-360 , Lo 180
 * Options: Additive true
 * Transparency: (0,0%) (30,100%) (80,80%)  (100,0%)
 */