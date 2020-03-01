package com.android.omise.donation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.omise.android.models.CardParam
import com.android.omise.data.model.Donation
import com.android.omise.data.repository.Failure
import com.android.omise.data.repository.TokenManager
import com.android.omise.data.usecase.DonateUseCase
import com.android.omise.util.Resource
import com.android.omise.util.Status
import javax.inject.Inject

private const val SATANGS = 100

class DonationViewModel @Inject constructor(
    private val donateUseCase: DonateUseCase,
    private val tokenManager: TokenManager

) : ViewModel() {

    var validationLiveData: MutableLiveData<String> = MutableLiveData()

    var donateLiveData: MutableLiveData<Resource<Any>> = MutableLiveData()

    fun donate(amount: String, cardParam: CardParam) {
        donateLiveData.value = Resource(Status.LOADING)

        tokenManager.createToken(cardParam)

        tokenManager.tokenGenerated = {
            if (it.id != null) {
                Log.d("Token", it.id)
                val amountInSatangs = amount.toDouble() * SATANGS
                val donation = Donation(cardParam.name, it.id!!, amountInSatangs.toInt())
                donateUseCase(donation) {
                    it.either(::handleFailure, ::handleSuccess)
                }
            } else {
                handleFailure(Failure.ServerError("Token not found, Try Again", Exception()))
            }

        }

        tokenManager.tokenNotGenerated = {
            handleFailure(Failure.ServerError(it.message, Exception()))
        }
    }

    private fun handleSuccess(response: Any?) {
        donateLiveData.value = Resource(Status.LOADED, response)
    }

    private fun handleFailure(failure: Failure) {
        donateLiveData.value = Resource(Status.FAILED, failure)
    }

}
