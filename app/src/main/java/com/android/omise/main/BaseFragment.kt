package com.android.omise.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.android.omise.R
import com.android.omise.util.hide
import com.android.omise.util.show
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_base_layout.view.*
import javax.inject.Inject

open abstract class BaseFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var progressBarView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val rootView = inflater.inflate(R.layout.fragment_base_layout, container, false)
        val content = inflater.inflate(getLayoutResourceId(), container, false)
        progressBarView = rootView.progressBarView
        rootView.fragmentFrame.addView(content)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setActivityTitle()
    }

    /**
     * Override this method to supply search_view id.
     */
    abstract fun getLayoutResourceId(): Int

    /**
     * This open function helps to set ActionBar title in Fragment, if we want.
     */
    open fun setActivityTitle() {}

    fun showLoading() {
        progressBarView.show()
    }

    fun hideLoading() {
        progressBarView.hide()
    }
}