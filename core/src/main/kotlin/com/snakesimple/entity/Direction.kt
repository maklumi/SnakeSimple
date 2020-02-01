package com.snakesimple.entity

enum class Direction {
    LEFT,
    RIGHT,
    UP,
    DOWN;

//    fun isOpposite(direction: Direction): Boolean {
//        return (
//                (this == LEFT && direction == RIGHT) ||
//                        (this == RIGHT && direction == LEFT) ||
//                        (this == UP && direction == DOWN) ||
//                        (this == DOWN && direction == UP)
//                )
//    }

    private fun getOpposite(): Direction {
        return when (this) {
            LEFT -> RIGHT
            RIGHT -> LEFT
            UP -> DOWN
            DOWN -> UP
        }
    }

    fun isOpposite(direction: Direction): Boolean {
        return getOpposite() == direction
    }

}