package com.util.map



object Validate {

    private const val DEFAULT_IS_NULL_MESSAGE = "The validated object is null."


    fun <T> notNull(obj: T) {
        notNull(obj, DEFAULT_IS_NULL_MESSAGE)
    }

    fun <T> notNull(obj: T?, message: String) {
        if (obj == null) {
            throw NullPointerException(message)
        }
    }

}
