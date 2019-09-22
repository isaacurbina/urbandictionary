package com.isaacurbna.urbandictionary.retrofit.interfaces

import com.isaacurbna.urbandictionary.model.data.RapidApiResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface OnlineApi {

    @GET("/define")
    fun getDefinitions(@Query("term") term: String): Single<RapidApiResponse>
}