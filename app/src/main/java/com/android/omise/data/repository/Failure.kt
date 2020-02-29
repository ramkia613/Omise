package com.android.omise.data.repository

/**
 * Base Class for handling errors/failures/exceptions.
 * Every feature specific failure should extend [FeatureFailure] class.
 */
private const val NETWORK_ERROR = "Please check your Internet connection"

sealed class Failure(val msg: String? = "", val error: Exception = Exception()) :
    Exception(msg, error) {
    class NetworkConnection() : Failure(NETWORK_ERROR)
    class ServerError(msg: String?, error: Exception) : Failure(msg, error)
    class DatabaseError : Failure()

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure : Failure()

    fun getTitle(): String = this.javaClass.simpleName
    override fun toString(): String = this.javaClass.name
}