package com.bignerdranch.android.yelpapp.api


import com.bignerdranch.android.yelpapp.data.YelpBusinessesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface YelpApi {
    @GET("businesses/search")
    suspend fun searchRestaurants(
        @Header("Authorization") authHeader: String,
        @Query("term") searchTerm: String,
        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String
    ): Result<YelpBusinessesResponse>
}
