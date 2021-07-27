package dev.eighteentech.test.network

import retrofit2.http.GET
import retrofit2.http.Query
import dev.eighteentech.test.common.Constants
import dev.eighteentech.test.entity.LatestRates
import dev.eighteentech.test.entity.Symbols

interface Api {
    @GET("symbols")
    suspend fun getSymbols(
        @Query("access_key") accessKey: String = Constants.API_KEY,
        @Query("format") format: Int = 1
    ): Symbols

    @GET("latest")
    suspend fun getLatestRates(
        @Query("access_key") accessKey: String = Constants.API_KEY,
        @Query("base") base: String,
        @Query("format") format: Int = 1
    ): LatestRates
}