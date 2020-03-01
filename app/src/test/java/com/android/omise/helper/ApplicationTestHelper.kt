package com.android.omise.helper

import android.content.Intent
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.android.omise.R
import com.android.omise.main.MainActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.robolectric.Robolectric
import org.robolectric.android.controller.ActivityController

object ApplicationTestHelper {

    val mainActivity: MainActivity = Robolectric.buildActivity(MainActivity::class.java).create().resume().get()


    fun initMainActivity(): MainActivity {
        val activity = Robolectric.buildActivity(MainActivity::class.java).create().resume().get()
        return activity
    }

    fun getMainActivityController(): ActivityController<MainActivity> {
        return Robolectric.buildActivity(MainActivity::class.java)
    }



    fun getString(stringResId: Int): String {
        return mainActivity.resources.getString(stringResId)
    }

    // TODO: stop using the following methods and move to FragmentScenario component

    fun startFragmentInMainContentFrame(fragment: Fragment) {
        mainActivity.navHostFragment.childFragmentManager.beginTransaction().replace(R.id.navHostFragment, fragment)
            .setPrimaryNavigationFragment(fragment)
            .commit()
    }

    fun addFragmentInMainContentFrame(fragment: Fragment) {
        mainActivity.navHostFragment.childFragmentManager.beginTransaction().add(fragment, null)
            .setPrimaryNavigationFragment(fragment)
            .commit()
    }
}
