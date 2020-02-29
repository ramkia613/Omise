package com.android.omise.data.usecase

import com.android.omise.data.model.Charity
import com.android.omise.data.repository.ApplicationRepository
import com.android.omise.data.repository.Failure
import com.android.omise.util.Either
import javax.inject.Inject

class GetCharitiesUseCase @Inject constructor(private val repository: ApplicationRepository) :
    UseCase<List<Charity>, Unit>() {

    override suspend fun run(params: Unit, useCache: Boolean): Either<Failure, List<Charity>?> {
        return repository.getCharity()
    }
}