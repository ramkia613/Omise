package com.android.omise.data.api

import com.android.omise.data.model.Charity
import retrofit2.Call
import retrofit2.http.GET

interface CharityApi {
    @GET("/Charity")
    fun getCharities(): Call<List<Charity>>
}