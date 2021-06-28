package com.vaibhav.taskify.util

sealed class Resource<T>(
    open val data: T? = null,
    open val message: String = "Oops something went wrong."
) {
    data class Success<T>(
        override val data: T? = null,
        override val message: String = "Oops something went wrong."
    ) :
        Resource<T>(data)

    data class Loading<T>(override val data: T? = null) : Resource<T>(data)
    data class Error<T>(
        override val message: String = "Oops something went wrong.",
        override val data: T? = null,
        val errorType: ErrorTYpe? = null
    ) : Resource<T>(data, message) {

        override fun equals(other: Any?): Boolean {
            return false
        }

        override fun hashCode(): Int {
            var result = message.hashCode()
            result = 31 * result + (data?.hashCode() ?: 0)
            result = 31 * result + (errorType?.hashCode() ?: 0)
            return result
        }

    }

    class Empty<T>() : Resource<T>()


}


