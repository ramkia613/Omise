package com.android.omise.data.repository

import com.android.omise.data.model.Charity
import com.android.omise.data.model.Donation
import com.android.omise.util.Either
import javax.inject.Inject

/**
 * This this Module related Repository, we can handle the data here. This layer will desired that to fetch data from local DB or Remote.
 *
 * Right now it'll fetch the data always from remote server.
 */
class ApplicationRepository @Inject constructor(
    private val networkDataSource: ApplicationNetworkDataSource
//    private val dBDataSource: DBDataSource
) {
    /**
     * @param useCache Parameter helps to handle cache information.
     *
     * We are not using cache in this task.
     */
    fun getCharity(useCache: Boolean = false): Either<Failure, List<Charity>?> {
        return networkDataSource.getCharities()
    }

    fun donate(donation: Donation): Either<Failure, Any?> {
        return networkDataSource.donate(donation)
    }

}