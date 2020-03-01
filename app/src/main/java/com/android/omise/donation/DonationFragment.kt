package com.android.omise.donation

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import co.omise.android.models.CardParam
import com.android.omise.R
import com.android.omise.data.model.Charity
import com.android.omise.data.repository.Failure
import com.android.omise.main.BaseFragment
import com.android.omise.util.*
import kotlinx.android.synthetic.main.donation_fragment.*
import javax.inject.Inject

class DonationFragment : BaseFragment() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    @Inject
    lateinit var encryptionUtil: EncryptionUtil

    private lateinit var viewModel: DonationViewModel
    private lateinit var charity: Charity

    override fun getLayoutResourceId() = R.layout.donation_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        charity = DonationFragmentArgs.fromBundle(arguments!!).charity
        Log.d("Donation", charity.toString())
        initViewModel()


        charityImageView.loadImage(this.requireContext(), charity.url)
        donate.setOnClickListener {
            val card = CardParam(
                cardNameEditText.cardName,
                creditCardEditText.cardNumber,
                expiryDateEditText.expiryMonth,
                expiryDateEditText.expiryYear,
                securityCodeEditText.securityCode
            )
            Log.d("amount", amountEditText.text.toString())
            Log.d("amount", "--${amountEditText.text.toString()}--")
            viewModel.donate(amountEditText.text.toString(), card)
        }


    }

    override fun setActivityTitle() {
        super.setActivityTitle()
        activity?.title = getString(R.string.donation)
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory).get(DonationViewModel::class.java)

        viewModel.donateLiveData.observe(viewLifecycleOwner, Observer {
            when (it?.status) {
                Status.LOADING -> {
                    showLoading()
                }
                Status.LOADED -> {
                    hideLoading()


                }
                Status.FAILED -> {
                    hideLoading()
                    showAlert(it.exception!!)
                }
            }
        })
    }

    private fun showAlert(failure: Failure) {

        val message = failure.message ?: getString(R.string.generic_error_message)
        this.requireContext().showAlertDialog(title = failure.getTitle(), message = message) {
            positiveButton(getString(R.string.ok))
        }
    }
}
