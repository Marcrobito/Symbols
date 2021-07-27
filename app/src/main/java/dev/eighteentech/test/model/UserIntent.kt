package dev.eighteentech.test.model

sealed class UserIntent {
    object LoadMainScreen : UserIntent()
    data class GetCurrencyRates(val currency: String) : UserIntent()
}