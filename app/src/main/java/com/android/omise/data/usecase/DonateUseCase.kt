package com.android.omise.data.usecase

import com.android.omise.data.model.Donation
import com.android.omise.data.repository.ApplicationRepository
import com.android.omise.data.repository.Failure
import com.android.omise.util.Either
import javax.inject.Inject

class DonateUseCase @Inject constructor(private val repository: ApplicationRepository) :
    UseCase<Any, Donation>() {

    override suspend fun run(params: Donation, useCache: Boolean): Either<Failure, Any?> {
        return repository.donate(params)
    }
}