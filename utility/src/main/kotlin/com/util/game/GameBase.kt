package com.util.game

import com.badlogic.gdx.Application
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.FrameBuffer
import com.badlogic.gdx.utils.Logger
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.util.screen.transition.ScreenTransition

abstract class GameBase : Game() {

    val assetManager = AssetManager()
    lateinit var batch: SpriteBatch

    // viewport just for transitions
    private var viewport: Viewport? = null

    private var currentScreen: Screen? = null
    private var nextScreen: Screen? = null

    private var currentFrameBuffer: FrameBuffer? = null
    private var nextFrameBuffer: FrameBuffer? = null

    private var time = 0f
    private var transition: ScreenTransition? = null
    private var renderedToTexture: Boolean = false
    private var transitionInProgress: Boolean = false

    private val duration: Float
        get() = if (transition == null) 0f else transition!!.duration

    private val isTransitionFinished: Boolean
        get() = time >= duration

    override fun create() {
        Gdx.app.logLevel = Application.LOG_DEBUG
        assetManager.logger.level = Logger.DEBUG
        batch = SpriteBatch()
        viewport = FitViewport(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())

        postCreate()
    }

    abstract fun postCreate()

    override fun dispose() {
        currentScreen?.dispose()
        nextScreen?.dispose()
        currentFrameBuffer?.dispose()
        nextFrameBuffer?.dispose()

        currentScreen = null
        nextScreen = null
        assetManager.dispose()
        batch.dispose()
    }

    @JvmOverloads
    fun setScreen(screen: Screen, transition: ScreenTransition? = null) {
        if (transitionInProgress) return
        if (currentScreen == screen) return

        this.transition = transition

        // screen size
        val width = Gdx.graphics.width
        val height = Gdx.graphics.height

        // create frame buffers
        currentFrameBuffer = FrameBuffer(Pixmap.Format.RGBA8888, width, height, false) // depth false used in 3d
        nextFrameBuffer = FrameBuffer(Pixmap.Format.RGBA8888, width, height, false)

        // start new transition
        nextScreen = screen
        nextScreen!!.show()
        nextScreen!!.resize(width, height)
        nextScreen!!.pause()
        time = 0f

        currentScreen?.pause()

    }

    override fun render() {
        val delta = Gdx.graphics.deltaTime

        if (nextScreen == null) {
            // no transition
            currentScreen?.render(delta)

        } else {
            // set flag to indicate that transition is in progress
            transitionInProgress = true

            val duration = duration
            time = Math.min(time + delta, duration)

            // render to texture only once for more performance
            if (!renderedToTexture) {
                renderScreensToTexture()
                renderedToTexture = true
            }

            updateTransition()
        }
    }

    override fun resize(width: Int, height: Int) {
        currentScreen?.resize(width, height)
        nextScreen?.resize(width, height)


        // set world size to width/height to keep 1:1 pixel per unit ratio
        viewport!!.setWorldSize(width.toFloat(), height.toFloat())
        viewport!!.update(width, height, true)
    }


    override fun pause() {
        currentScreen?.pause()
    }

    override fun resume() {
        currentScreen?.resume()
    }

    private fun renderScreensToTexture() {
        // render current screen to FBO (texture)
        if (currentScreen != null) {
            currentFrameBuffer!!.begin()
            currentScreen!!.render(0f) // 0 to render 1st frame
            currentFrameBuffer!!.end()
        }

        // render next screen to FBO (texture)
        nextFrameBuffer!!.begin()
        nextScreen!!.render(0f) // 0 to render 1st frame
        nextFrameBuffer!!.end()
    }

    private fun updateTransition() {
        if (transition == null || isTransitionFinished) {
            // just finished or no transition set
            currentScreen?.hide()


            // resume next screen and enable input
            nextScreen!!.resume()

            // switch screens and reset
            currentScreen = nextScreen
            nextScreen = null
            transition = null
            currentFrameBuffer!!.dispose()
            nextFrameBuffer!!.dispose()
            currentFrameBuffer = null
            nextFrameBuffer = null
            renderedToTexture = false
            transitionInProgress = false
            return
        }

        // calculate percentage
        val percentage = time / duration

        // get textures from FBOs
        // NOTE: these texture are auto disposed when FBO is disposed
        val currentScreenTexture = currentFrameBuffer!!.colorBufferTexture
        val nextScreenTexture = nextFrameBuffer!!.colorBufferTexture

        // render transition to screen
        batch.projectionMatrix = viewport!!.camera.combined
        transition!!.render(batch, currentScreenTexture, nextScreenTexture, percentage)
    }

}