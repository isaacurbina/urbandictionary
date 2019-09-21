package com.isaacurbna.urbandictionary.retrofit.interfaces

import com.isaacurbna.urbandictionary.retrofit.model.RapidApiResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RapidApiInterface {

    @GET("/define")
    fun getDefinitions(@Query("term") term: String): Single<RapidApiResponse>
}