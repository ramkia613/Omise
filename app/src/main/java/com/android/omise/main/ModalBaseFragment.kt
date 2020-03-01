package com.android.omise.main

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.android.omise.R
import dagger.android.support.DaggerAppCompatDialogFragment
import kotlinx.android.synthetic.main.fragment_modal_base_layout.*
import kotlinx.android.synthetic.main.fragment_modal_base_layout.view.*
import javax.inject.Inject

/**
 * modal base class which provides slide in/slide out animation for mobile and tab
 */
abstract class ModalBaseFragment : DaggerAppCompatDialogFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory


    enum class AnimationType {
        NONE,
        SLIDE_IN,
        SLIDE_OUT
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val rootView = inflater.inflate(R.layout.fragment_modal_base_layout, container, false)

        val childContent = inflater.inflate(getLayoutResourceId(), container, false)
        rootView.baseContentFrame.addView(childContent)
        return rootView
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return object : Dialog(requireContext(), R.style.CustomDialogTheme) {
            // hardware back button press
            override fun onBackPressed() {

            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    fun setModalTitle(@StringRes titleRes: Int) {
        baseModalTitle.text = getString(titleRes)
    }

    protected open fun onBackPressed() {
        // do nothing, function to be override
    }

    open fun dismissDialog() {
        dismiss()
    }

    /**
     * Override this method to supply layout id.
     */
    abstract fun getLayoutResourceId(): Int


}