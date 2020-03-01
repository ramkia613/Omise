package com.android.omise.data.api

import com.android.omise.data.model.Charity
import com.android.omise.data.model.Donation
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CharityApi {
    @GET("/charities")
    fun getCharities(): Call<List<Charity>>

    @POST("/donations")
    fun donate(@Body donation: Donation): Call<Any>
}