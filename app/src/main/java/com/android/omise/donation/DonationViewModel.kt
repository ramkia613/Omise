package com.android.omise.donation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.omise.android.models.CardBrand
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

    var validationLiveData: MutableLiveData<List<String>> = MutableLiveData()

    var donateLiveData: MutableLiveData<Resource<Any>> = MutableLiveData()

    fun donate(
        amount: String,
        cardParam: CardParam,
        cardBrand: CardBrand?
    ) {
        validate(amount, cardParam, cardBrand)
        donateLiveData.value = Resource(Status.LOADING)

        tokenManager.createToken(cardParam)

        tokenManager.tokenGenerated = {
            if (it.id != null) {
                val amountInSatangs = amount.toDouble() * SATANGS
                val donation = Donation(cardParam.name, it.id!!, amountInSatangs.toInt())
                donateUseCase(donation) {
                    it.either(::handleFailure, ::handleSuccess)
                }
            } else {
                handleFailure(Failure.ServerError(TOKEN_NOT_FOUND, Exception()))
            }

        }

        tokenManager.tokenNotGenerated = {
            handleFailure(Failure.ServerError(it.message, Exception()))
        }
    }

    private fun validate(
        amount: String,
        cardParam: CardParam,
        cardBrand: CardBrand?
    ) {
        val validationList = arrayListOf<String>()


        cardBrand?.let {
            validationList.add(INVALID_CARD)
        }

        if (cardParam.name.isBlank()) {
            validationList.add(NAME_ERROR)
        }
        if (cardParam.expirationMonth == 0) {
            validationList.add(MONTH_ERROR)
        }

        if (cardParam.expirationYear == 0) {
            validationList.add(YEAR_ERROR)
        }
        if (cardParam.securityCode.isEmpty()) {
            validationList.add(SECURITY_CODE_ERROR)
        }
        if (amount.isEmpty()) {
            validationList.add(AMOUNT_ERROR)
        }

        if (!validationList.isEmpty()) {
            validation(validationList)
            return
        }
    }

    private fun handleSuccess(response: Any?) {
        donateLiveData.value = Resource(Status.LOADED, response)
    }

    private fun handleFailure(failure: Failure) {
        donateLiveData.value = Resource(Status.FAILED, failure)
    }

    private fun validation(error: ArrayList<String>) {
        validationLiveData.value = error
    }

    companion object {
        private const val INVALID_CARD = "InValid card"
        private const val NAME_ERROR = "Name can not empty"
        private const val MONTH_ERROR = "Month can not Empty"
        private const val YEAR_ERROR = "Year can not Empty"
        private const val SECURITY_CODE_ERROR = "Security code can not empty"
        private const val AMOUNT_ERROR = "Amount can not empty"
        private const val TOKEN_NOT_FOUND = "Token not found, Try Again"

    }
}
