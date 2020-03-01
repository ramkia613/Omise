package com.android.omise.donation

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import co.omise.android.models.CardParam
import com.android.omise.R
import com.android.omise.data.model.Charity
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
            creditCardEditText.cardBrand
            viewModel.donate(amountEditText.text.toString(), card, creditCardEditText.cardBrand)
        }
    }

    override fun setActivityTitle() {
        super.setActivityTitle()
        activity?.title = getString(R.string.donation)
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory).get(DonationViewModel::class.java)

        viewModel.validationLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {

                //                showAlert(title = getString(R.string.invaild_input), error = it)
            }
        })

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
                    showAlert(error = it.exception!!.message)
                }
            }
        })
    }

    private fun showAlert(title: String = getString(R.string.error_title), error: String?) {
        val message = error ?: getString(R.string.generic_error_message)
        this.requireContext()
            .showAlertDialog(title = title, message = message) {
                positiveButton(getString(R.string.ok))
            }
    }
}
