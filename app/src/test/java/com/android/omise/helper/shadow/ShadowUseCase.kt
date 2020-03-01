package com.android.omise.helper.shadow

import com.android.omise.data.repository.Failure
import com.android.omise.data.usecase.UseCase
import com.android.omise.util.Either
import org.robolectric.annotation.Implementation
import org.robolectric.annotation.Implements

@Implements(UseCase::class)
class ShadowUseCase<out Type, in Params> where Type : Any {

    @Suppress("UNUSED_PARAMETER")
    @Implementation
    operator fun invoke(
        params: Params,
        useCache: Boolean = true,
        onResult: (Either<Failure, Type?>) -> Unit = {}
    ) {
    }
}
