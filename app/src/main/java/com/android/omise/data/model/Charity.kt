package com.android.omise.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Charity(
    val id: Int,
    val name: String,
    @SerializedName("logo_url")
    val url: String
) : Parcelable

data class Donation(
    val name: String,
    val token: String,
    val amount: Int
)