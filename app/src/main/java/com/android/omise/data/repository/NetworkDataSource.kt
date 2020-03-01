package com.android.omise.data.repository

import com.android.omise.util.Either
import okhttp3.ResponseBody
import retrofit2.Call
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.UnknownHostException
import java.nio.CharBuffer


const val READ_TIMEOUT = 60L
const val WRITE_TIMEOUT = 60L
const val CONNECT_TIMEOUT = 60L

abstract class NetworkDataSource() {

    fun <T, R> request(call: Call<T>, default: R): Either<Failure, R> {
        return try {
            val response = call.execute()
            when (response.isSuccessful) {
                true -> Either.Right(processRequest(response.body()) ?: default)
                false -> {
                    val message = getErrorMessage(response.errorBody()!!)
                    Either.Left(processServiceFailure(message))
                }
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
        val mesage = t?.let {
            it
        };run {
            "Something went wrong!"
        }
        return Failure.ServerError(
            t.toString(),
            Exception()
        )
    }

    fun getErrorMessage(errorBody: ResponseBody): String? {
        val inputStream = errorBody.byteStream()
        val reader = BufferedReader(InputStreamReader(inputStream))
        try {
            val charBuffer: CharBuffer = CharBuffer.allocate(errorBody.contentLength().toInt())
            reader.read(charBuffer)
            return charBuffer.flip().toString()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }
}