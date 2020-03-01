package com.android.omise.charity

import com.android.omise.ApplicationRunner
import com.android.omise.R
import com.android.omise.helper.ApplicationTestHelper
import com.nhaarman.mockitokotlin2.spy
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertNotNull
import kotlin.test.junit.JUnitAsserter.assertEquals

@RunWith(ApplicationRunner::class)
class CharityFragmentTest {

    private lateinit var charityFragment: CharityFragment

    private fun startCharityFragment() {
        charityFragment = spy(CharityFragment())
        ApplicationTestHelper.addFragmentInMainContentFrame(charityFragment)
        assertNotNull(charityFragment)
    }

    @Test
    fun checkCharityFragment() {
        startCharityFragment()
        assertNotNull(charityFragment.activity?.title)
        assertEquals(
            null,
            charityFragment.getString(R.string.charities),
            charityFragment.activity?.title
        )
    }
}