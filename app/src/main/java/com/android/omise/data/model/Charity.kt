package com.android.omise.data.model

import android.os.Parcelable
import co.omise.android.models.CardParam
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

data class Pay(
    val amount: Int,
    val cardParam: CardParam
)
