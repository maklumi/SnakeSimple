package com.snakeashley.system

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IntervalSystem
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.utils.Array
import com.snakeashley.CoinComponent
import com.snakeashley.SnakeComponent
import com.snakeashley.common.*
import com.snakeashley.system.passive.EntityFactorySystem
import com.snakeashley.system.passive.GameSoundSystem
import com.snakesimple.common.GameManager
import com.snakesimple.common.GameState
import com.snakesimple.config.GameConfig
import ktx.ashley.get


class CollisionSystem : IntervalSystem(GameConfig.MOVE_TIME) {

    companion object {
        private val SNAKE_FAMILY: Family = Family.all(SnakeComponent::class.java).get()
        private val COIN_FAMILY: Family = Family.all(CoinComponent::class.java).get()
    }

    private lateinit var factory: EntityFactorySystem
    private lateinit var listener: GameSoundSystem

    override fun addedToEngine(engine: Engine) {
        factory = engine.getSystem(EntityFactorySystem::class.java)
        listener = engine.getSystem(GameSoundSystem::class.java)
    }

    override fun updateInterval() {
        val snakes: ImmutableArray<Entity> = engine.getEntitiesFor(SNAKE_FAMILY)
        val coins: ImmutableArray<Entity> = engine.getEntitiesFor(COIN_FAMILY)

        // collision between head <-> coin
        snakes.forEach { snakeEntity ->
            coins.forEach { coinEntity ->
                val snakeComponent = snakeEntity[SNAKE_COMPONENT]!!
                val coinComponent = coinEntity[COIN]!!

                if (coinComponent.available && overlaps(snakeComponent.head, coinEntity)) {
                    coinComponent.available = false // so allow respawn

                    // insert body part at coin position
                    val pos = snakeComponent.head[POSITION]!!
                    val bodyPart = factory.createBodyPart(pos.x, pos.y) // added but not attached to head
                    snakeComponent.bodyParts.insert(0, bodyPart)
                    GameManager.score += GameConfig.COIN_SCORE
                    listener.hitCoin()
                }
            }
        }

        // collision between head <-> body parts
        for (snakeEntity in snakes) {
            val snake = snakeEntity[SNAKE_COMPONENT]!!

            val entities = Array.ArrayIterable<Entity>(snake.bodyParts)
            for (bodyPartEntity in entities) {
                val bodyPart = bodyPartEntity[BODY_PART]!!

                if (bodyPart.justAdded) {
                    bodyPart.justAdded = false
                    continue
                }

                if (overlaps(snake.head, bodyPartEntity)) {
                    GameManager.state = GameState.GAME_OVER
                    GameManager.saveHighScore()
                    listener.lose()
                }
            }
        }
    }

    private fun overlaps(first: Entity, second: Entity): Boolean {
        val firstBounds = first[BOUNDS]!!
        val secondBounds = second[BOUNDS]!!
        return Intersector.overlaps(firstBounds.rectangle, secondBounds.rectangle)
    }
}