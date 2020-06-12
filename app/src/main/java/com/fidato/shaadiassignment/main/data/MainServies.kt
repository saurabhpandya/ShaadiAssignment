package com.fidato.shaadiassignment.main.data

import com.fidato.shaadiassignment.model.MatchesData
import retrofit2.http.GET
import retrofit2.http.Query

interface MainServies {
    @GET("api/?")
    suspend fun getMatches(@Query("results") noOfResults: Int): MatchesData
}