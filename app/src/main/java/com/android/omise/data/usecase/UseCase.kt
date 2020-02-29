package com.android.omise.data.usecase

import com.android.omise.data.repository.Failure
import com.android.omise.util.Either
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

abstract class UseCase<out Type : Any, in Params> : CoroutineScope {

    private var job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    abstract suspend fun run(params: Params, useCache: Boolean = true): Either<Failure, Type?>
    operator fun invoke(
        params: Params,
        useCache: Boolean = true,
        onResult: (Either<Failure, Type?>) -> Unit = {}
    ) {
        if (job.isCancelled) job = Job()
        launch {
            val result = async(dispatcher) {
                run(params, useCache)
            }
            onResult(result.await())
        }
    }

    // cancel the job in progress
    fun cancel() {
        job.cancel()
    }

    companion object {
        lateinit var dispatcher: CoroutineDispatcher
    }
}