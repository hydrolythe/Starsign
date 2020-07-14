package com.example.starsign.data



/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class Resulting<out T : Any> {

    data class Success<out T : Any>(val data: T) : Resulting<T>()
    data class Error(val exception: Exception) : Resulting<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }
}
