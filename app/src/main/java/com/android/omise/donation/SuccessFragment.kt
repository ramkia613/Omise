package com.android.omise.donation

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.android.omise.R
import com.android.omise.data.model.Charity
import com.android.omise.main.ModalBaseFragment
import com.android.omise.util.loadImage
import kotlinx.android.synthetic.main.fragment_modal_base_layout.*
import kotlinx.android.synthetic.main.fragment_success.*

class SuccessFragment : ModalBaseFragment() {

    lateinit var charity: Charity

    override fun getLayoutResourceId(): Int = R.layout.fragment_success

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            charity = it.getParcelable(PARAM_CHARITY)!!
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setModalTitle(R.string.payment_successfull)
        charityImageView.loadImage(this.requireContext(), charity.url)
        mesageTextView.text = String.format(getString(R.string.success_message), charity.name)
        dismissButton.setOnClickListener {
            dismiss()
        }
        close.setOnClickListener {
            dismiss()
        }
    }

    override fun dismiss() {
        findNavController().popBackStack(R.id.charityDest, false)
        super.dismiss()
    }

    companion object {
        const val PARAM_CHARITY = "param_charity"
        fun newInstance(
            charity: Charity
        ): SuccessFragment {
            val fragment = SuccessFragment()
            fragment.arguments = Bundle().apply {
                putParcelable(PARAM_CHARITY, charity)
            }
            return fragment
        }
    }


}
