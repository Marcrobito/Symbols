package dev.eighteentech.test.model

import dev.eighteentech.test.entity.LatestRates
import dev.eighteentech.test.entity.Symbols
import java.util.logging.Filter

sealed class State(open val response: Response<Any>){
    data class MainScreenState(override val response: Response<List<Pair<String,String>>>, val filter: String? = null): State(response)
    data class ConverterState(override val response: Response<List<Pair<String,Double>>>): State(response)
}
