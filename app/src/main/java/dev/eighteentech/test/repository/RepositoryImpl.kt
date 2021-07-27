package dev.eighteentech.test.repository

import android.util.Log
import dev.eighteentech.test.entity.LatestRates
import dev.eighteentech.test.entity.Symbols
import dev.eighteentech.test.model.Response
import dev.eighteentech.test.network.Api
import dev.eighteentech.test.ui.DialogFragment

private const val TAG = "RepositoryImpl"
class RepositoryImpl(private val api:Api): Repository {
    override suspend fun fetchSymbols(): Response<List<Pair<String,String>>> {
        return try {
            val result = api.getSymbols()
            if (!result.success || result.symbols == null){
                Response.Fail(Exception(result.error!!.info))
            }else{
                val list:List<Pair<String,String>> = result.symbols.toList()
                Response.Success(list)
            }
        }catch (e:Exception){
            Response.Fail(e)
        }
    }

    override suspend fun fetchLatestRatesForSymbol(symbol: String): Response<List<Pair<String,Double>>>  {
         return try {
             val result = api.getLatestRates(base = symbol)
             if (!result.success || result.rates == null){
                 Log.d(TAG, result.error.toString())
                 Response.Fail(Exception(result.error!!.type))
             }else{
                 val rates:List<Pair<String,Double>> = result.rates.toList()
                 Response.Success(rates)
             }
         }catch (e:Exception){
             Log.d(TAG, e.toString())
             Response.Fail(e)
         }
    }
}