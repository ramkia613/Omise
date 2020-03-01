package com.android.omise.charity

import com.android.omise.ApplicationRunner
import com.android.omise.R
import com.android.omise.data.model.Charity
import com.android.omise.data.repository.Failure
import com.android.omise.helper.ApplicationTestHelper
import com.android.omise.util.Resource
import com.android.omise.util.Status
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.shadows.ShadowDialog
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

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
            charityFragment.getString(R.string.charities),
            charityFragment.activity?.title
        )
    }

    @Test
    fun shouldSuccess() {
        startCharityFragment()
        val charity: Charity = mock()
        whenever(charity.id).thenReturn(1)
        whenever(charity.url).thenReturn("http://localhost:8080")
        whenever(charity.name).thenReturn("test")


        val charities: List<Charity> = arrayListOf(charity, charity)

        charityFragment.viewModel.charitiesLiveData.value =
            Resource(Status.LOADED, charities)
        assertEquals(charityFragment.charityAdapter.itemCount, charities.size)

    }

    @Test
    fun shouldFailure() {
        startCharityFragment()

        charityFragment.viewModel.charitiesLiveData.value =
            Resource(Status.FAILED, Failure.NetworkConnection())

        val dialog = ShadowDialog.getLatestDialog()
        assertTrue(dialog.isShowing)

    }
}