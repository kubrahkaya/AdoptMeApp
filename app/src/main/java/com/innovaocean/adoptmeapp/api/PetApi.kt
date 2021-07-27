package com.innovaocean.adoptmeapp.api

import com.innovaocean.adoptmeapp.data.BreedResponse
import com.innovaocean.adoptmeapp.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PetApi {

    @GET("breeds/search")
    suspend fun searchForBreedsByName(
        @Query("x-api-key") apiKey: String = Constants.API_KEY,
        @Query("q") searchQuery: String
    ): Response<List<BreedResponse>>

}