package com.snakeashley.common

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.assets.AssetManager
import com.snakeashley.*
import com.snakesimple.assets.Descriptor
import com.snakesimple.assets.RegionNames
import com.snakesimple.config.GameConfig
import ktx.ashley.entity


class EntityFactory(private val engine: PooledEngine, assetManager: AssetManager) {

    private val gameAtlas = assetManager.get(Descriptor.GAME_PLAY)

    // linking snakeHead entity and body part entity
    // be careful to dispose them separately
    fun createSnake(): Entity {
        return engine.entity {
            with<SnakeComponent> {
                head = createSnakeHead()
            }
        }
    }

    private fun createSnakeHead(): Entity {
        return engine.entity {
            with<DirectionComponent>()
            with<MovementComponent>()
            with<PlayerComponent>()
            val position = with<Position>()
            val dimension = with<Dimension> {
                width = GameConfig.SNAKE_SIZE
                height = GameConfig.SNAKE_SIZE
            }
            with<Bounds> {
                rectangle.set(position.x, position.y, dimension.width, dimension.height)
            }
            with<TextureComponent> {
                region = gameAtlas.findRegion(RegionNames.HEAD)
            }
        }
    }

    fun createCoin() {
        engine.entity {
            with<Position>()
            with<Dimension>()
            with<Bounds>()
            with<CoinComponent>()
            with<TextureComponent> {
                region = gameAtlas.findRegion(RegionNames.COIN)
            }
        }
    }

    fun createBodyPart(x: Float, y: Float): Entity {
        return engine.entity {
            val pos = with<Position> {
                this.x = x
                this.y = y
            }
            val dim = with<Dimension> {
                width = GameConfig.SNAKE_SIZE
                height = GameConfig.SNAKE_SIZE
            }
            with<Bounds> {
                rectangle.setPosition(pos.x, pos.y)
                rectangle.setSize(dim.width, dim.height)
            }
            with<BodyPartComponent>()
            with<TextureComponent> {
                region = gameAtlas.findRegion(RegionNames.BODY)
            }
        }
    }
}