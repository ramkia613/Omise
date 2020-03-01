package com.android.omise.utils

import android.os.Build
import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import org.hamcrest.Matcher
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.math.abs

object WaitUtil {

    private val defaultWaitingTime: Long = 10 * 1000

    fun waitingWithElement(viewMatch: Matcher<View>): Boolean {
        return withElement(viewMatch, defaultWaitingTime)
    }

    fun waitingElement(viewMatch: Matcher<View>, second: Long): Boolean {
        val millisecond = second * 1000
        return withElement(viewMatch, millisecond)
    }

    private fun withElement(viewMatch: Matcher<View>, millisecond: Long): Boolean {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val time = LocalDateTime.now()
            while (ChronoUnit.MILLIS.between(time, LocalDateTime.now()) < millisecond) {
                try {
                    onView(viewMatch).check(matches(isDisplayed()))
                    return true
                } catch (e: NoMatchingViewException) {
                    e.printStackTrace()
                }
            }
        } else {

            val time = Calendar.getInstance().time
            while (abs((Calendar.getInstance().time.time - time.time)) < millisecond) {
                try {
                    onView(viewMatch).check(matches(isDisplayed()))
                    return true
                } catch (e: NoMatchingViewException) {
                    e.printStackTrace()
                }
            }
        }
        return false
    }

}