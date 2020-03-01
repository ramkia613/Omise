package com.android.omise.data.repository

import com.android.omise.data.api.CharityApi
import com.android.omise.data.model.Charity
import com.android.omise.data.model.Donation
import com.android.omise.util.Either
import javax.inject.Inject

class ApplicationNetworkDataSource @Inject constructor(private val charityApi: CharityApi) :
    NetworkDataSource() {

    fun getCharities(): Either<Failure, List<Charity>?> {
        return request(charityApi.getCharities(), null)
    }

    fun donate(donation: Donation): Either<Failure, Any?> {
        return request(charityApi.donate(donation), null)
    }
}