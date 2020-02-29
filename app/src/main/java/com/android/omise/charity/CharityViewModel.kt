package com.android.omise.charity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.omise.data.model.Charity
import com.android.omise.data.repository.Failure
import com.android.omise.data.usecase.GetCharitiesUseCase
import com.android.omise.util.Resource
import com.android.omise.util.Status
import javax.inject.Inject

class CharityViewModel @Inject constructor(
    private val getCharitiesUseCase: GetCharitiesUseCase
) : ViewModel() {

    var charitiesLiveData: MutableLiveData<Resource<List<Charity>>> = MutableLiveData()

    /**
     * This will request the call while initializing this ViewModel
     */
    init {
        getCharities()
    }

    /**
     * This function will request the Charities to Repository using UseCase.
     */
    fun getCharities() {
        charitiesLiveData.value = Resource(Status.LOADING)
        getCharitiesUseCase(Unit) {
            it.either(::handleFailure, ::handleSuccess)
        }
    }


    private fun handleSuccess(response: List<Charity>?) {
        charitiesLiveData.value = Resource(Status.LOADED, response)
    }

    private fun handleFailure(failure: Failure) {
        charitiesLiveData.value = Resource(Status.FAILED, failure)
    }
}