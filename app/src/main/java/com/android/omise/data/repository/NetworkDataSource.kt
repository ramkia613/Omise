package com.android.omise.data.repository

import com.android.omise.util.Either
import retrofit2.Call
import java.net.UnknownHostException

const val READ_TIMEOUT = 60L
const val WRITE_TIMEOUT = 60L
const val CONNECT_TIMEOUT = 60L

abstract class NetworkDataSource() {

    fun <T, R> request(call: Call<T>, default: R): Either<Failure, R> {
        return try {
            val response = call.execute()
            when (response.isSuccessful) {
                true -> Either.Right(processRequest(response.body()) ?: default)
                false -> Either.Left(processServiceFailure(response.errorBody()))
            }
        } catch (exception: UnknownHostException) {
            Either.Left(Failure.NetworkConnection())
        } catch (exception: Exception) {
            Either.Left(
                Failure.ServerError(
                    exception.message,
                    exception
                )
            )
        }
    }

    private fun <T, R> processRequest(t: T): R? {
        return t as R
    }

    private fun <T> processServiceFailure(t: T): Failure {
        return Failure.ServerError(
            "Server returned error",
            Exception()
        )
    }
}