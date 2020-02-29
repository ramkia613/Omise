package com.android.omise.charity

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.omise.R
import com.android.omise.data.model.Charity
import com.android.omise.data.repository.Failure
import com.android.omise.main.BaseFragment
import com.android.omise.util.Status
import com.android.omise.util.negativeButton
import com.android.omise.util.positiveButton
import com.android.omise.util.showAlertDialog
import kotlinx.android.synthetic.main.charity_fragment.*

class CharityFragment : BaseFragment() {

    private lateinit var viewModel: CharityViewModel

    private var charities: MutableList<Charity> = mutableListOf()
    private lateinit var charityAdapter: CharityAdapter


    override fun getLayoutResourceId(): Int = R.layout.charity_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        initViewModal()

    }

    override fun setActivityTitle() {
        super.setActivityTitle()
        activity?.title = getString(R.string.charities)
    }

    /**
     * Setup the RecyclerView.
     */
    private fun setupRecyclerView() {
        charityAdapter = CharityAdapter(charities)
        charityRecyclerView.adapter = charityAdapter
        charityAdapter.selectCharity = {
            //findNavController().navigate()
        }
    }

    /**
     * This method will update the List of Charities from the response of Load and Refresh
     * @param charityList
     */
    private fun updateList(charityList: List<Charity>?) {
        charities.clear()
        charityList?.let {
            charities.addAll(it)
        }
        charityAdapter.notifyDataSetChanged()
    }

    private fun initViewModal() {
        viewModel = ViewModelProvider(this, viewModelFactory).get(CharityViewModel::class.java)

        viewModel.charitiesLiveData.observe(viewLifecycleOwner, Observer {
            when (it?.status) {
                Status.LOADING -> {
                    showLoading()
                }
                Status.LOADED -> {
                    hideLoading()
                    updateList(it.data)

                }
                Status.FAILED -> {
                    hideLoading()
                    updateList(null)
                    showAlert(it.exception!!)

                }
            }
        })
    }

    private fun showAlert(failure: Failure) {

        val message = failure.message ?: getString(R.string.generic_error_message)
        this.requireContext().showAlertDialog(title = failure.getTitle(), message = message) {
            positiveButton(getString(R.string.retry)) {
                viewModel.getCharities()
            }
            negativeButton {
            }
        }
    }

}
