package com.android.omise.main

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.android.omise.R
import com.android.omise.utils.WaitUtil
import org.hamcrest.CoreMatchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun donationSuccessTest() {

        onView(withText(activityTestRule.activity.getString(R.string.charities)))
        selectItem(0)

//        Donation page
        onView(withText(activityTestRule.activity.getString(R.string.donate)))

        onView(withId(R.id.creditCardEditText)).perform(typeText("4242424242424242"))
        onView(withId(R.id.cardNameEditText)).perform(typeText("John Doe"))
        onView(withId(R.id.scrollView)).perform(swipeUp())
        onView(withId(R.id.expiryDateEditText)).perform(typeText("1020"))
        onView(withId(R.id.securityCodeEditText)).perform(typeText("123"))
        onView(withId(R.id.amountEditText)).perform(typeText("20"))
        onView(withId(R.id.donate)).perform(click())

        WaitUtil.waitingWithElement(withId(R.id.baseModalTitle))
        onView(allOf(withId(R.id.baseModalTitle), withText(R.string.payment_successfull)))
        onView(withId(R.id.dismissButton)).perform(click())

        //Return to Charity Page
        onView(withText(activityTestRule.activity.getString(R.string.charities)))

    }

    @Test
    fun donationFailureTest() {

        onView(withText(activityTestRule.activity.getString(R.string.charities)))
        selectItem(0)

//        Donation page
        onView(withText(activityTestRule.activity.getString(R.string.donate)))

        onView(withId(R.id.creditCardEditText)).perform(typeText("424242424242424"))
        onView(withId(R.id.cardNameEditText)).perform(typeText("John Doe"))
        onView(withId(R.id.scrollView)).perform(swipeUp())
        onView(withId(R.id.expiryDateEditText)).perform(typeText("1020"))
        onView(withId(R.id.securityCodeEditText)).perform(typeText("123"))
        onView(withId(R.id.amountEditText)).perform(typeText("20"))
        onView(withId(R.id.donate)).perform(click())

        //Failure alert
        onView(withId(R.id.alertTitle))
            .inRoot(isDialog())
            .check(matches(isDisplayed()))
    }

    private fun selectItem(index: Int) {
        val charityList = withId(R.id.charityRecyclerView)
        WaitUtil.waitingWithElement(charityList)

        onView(allOf(withId(R.id.charityRecyclerView))).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                index, click()
            )
        )
    }

}