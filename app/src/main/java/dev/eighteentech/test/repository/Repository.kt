package dev.eighteentech.test.repository

import dev.eighteentech.test.entity.LatestRates
import dev.eighteentech.test.entity.Symbols
import dev.eighteentech.test.model.Response

interface Repository {
    suspend fun fetchSymbols(): Response<List<Pair<String,String>>>
    suspend fun fetchLatestRatesForSymbol(symbol:String):Response<List<Pair<String,Double>>>
}