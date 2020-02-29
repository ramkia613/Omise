package com.android.omise.util

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import com.android.omise.R


fun View.hide() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.isVisible(): Boolean {
    return this.visibility == View.VISIBLE
}

fun View.setVisibility(isVisible: Boolean) {
    if (isVisible) show() else hide()
}

/**
 * This extension function helps to apply the navigation transition for all fragment, instead of adding transition for each fragment in nav_graph
 */
fun NavController.navigateWithAnim(directions: NavDirections) {
    this.navigate(
        directions.actionId,
        directions.arguments,
        NavOptions.Builder()
            .setEnterAnim(R.anim.slide_anim_in)
            .setExitAnim(R.anim.slide_anim_out)
            .setPopEnterAnim(R.anim.slide_pop_anim_in)
            .setPopExitAnim(R.anim.slide_pop_anim_out).build()
    )
}

fun NavController.navigateWithClearStack(@IdRes destId: Int, args: Bundle? = null) {
    this.navigate(
        destId,
        args,
        NavOptions.Builder()
            .setPopUpTo(R.id.navGraph, true)
            .setLaunchSingleTop(true).build()
    )
}


fun NavController.navigateWithAnim(@IdRes destId: Int, args: Bundle? = null) {
    this.navigate(
        destId,
        args,
        NavOptions.Builder()
            .setEnterAnim(R.anim.slide_anim_in)
            .setExitAnim(R.anim.slide_anim_out)
            .setPopEnterAnim(R.anim.slide_pop_anim_in)
            .setPopExitAnim(R.anim.slide_pop_anim_out).build()
    )
}

fun Context.showAlertDialog(
    positiveButtonColor: Int = R.color.primary_color,
    negativeButtonColor: Int = R.color.warning,
    title: String,
    message: String,
    dialogBuilder: AlertDialog.Builder.() -> Unit
) {
    val builder = AlertDialog.Builder(this)
    builder.setTitle(title)
        .setMessage(message)
        .dialogBuilder()

    val dialog = builder.create()
    dialog.show()
    dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
        .setTextColor(ContextCompat.getColor(this, negativeButtonColor))
    dialog.getButton(AlertDialog.BUTTON_POSITIVE)
        .setTextColor(ContextCompat.getColor(this, positiveButtonColor))
}

fun AlertDialog.Builder.positiveButton(
    text: String = context.getString(R.string.alert_positive),
    handleClick: (which: Int) -> Unit = {}
) {
    this.setPositiveButton(text) { _, which -> handleClick(which) }
}

fun AlertDialog.Builder.negativeButton(
    text: String = context.getString(R.string.alert_negative),
    handleClick: (which: Int) -> Unit = {}
) {
    this.setNegativeButton(text) { _, which -> handleClick(which) }
}
