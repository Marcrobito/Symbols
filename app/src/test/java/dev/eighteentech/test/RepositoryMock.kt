package dev.eighteentech.test

import dev.eighteentech.test.model.Response
import dev.eighteentech.test.repository.Repository
import java.lang.Exception

class RepositoryMock : Repository {


    override suspend fun fetchSymbols() = successSymbolsResponse

    override suspend fun fetchLatestRatesForSymbol(symbol: String): Response<List<Pair<String, Double>>> = Response.Success(listOf(Pair("USD",1.0)))

    companion object{
        private val listSymbols = listOf<Pair<String,String>>(Pair("Euro","EUR"))
        val successSymbolsResponse = Response.Success(listSymbols)
    }
}
