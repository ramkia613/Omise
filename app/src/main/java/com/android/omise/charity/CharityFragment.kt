package com.android.omise.charity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.omise.R
import com.android.omise.main.BaseFragment

class CharityFragment : BaseFragment() {

    private lateinit var viewModel: CharityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.charity_fragment, container, false)
    }


    override fun getLayoutResourceId(): Int = R.layout.charity_fragment

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(CharityViewModel::class.java)


        viewModel.charitiesLiveData.observe(viewLifecycleOwner, Observer {

        })


    }

}
