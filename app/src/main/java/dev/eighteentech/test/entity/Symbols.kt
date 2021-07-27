package dev.eighteentech.test.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Symbols(
    @Json(name = "success") val success: Boolean,
    @Json(name = "error") val error: RequestError?,
    @Json(name = "symbols") val symbols: Map<String,String>?
)

@JsonClass(generateAdapter = true)
data class RequestError(
    @Json(name = "code") val code: Int,
    @Json(name = "type") val type: String,
    @Json(name = "info") val info: String?
)

@JsonClass(generateAdapter = true)
data class LatestRates(
    @Json(name = "success") val success: Boolean,
    @Json(name = "error") val error: RequestError?,
    @Json(name = "timestamp") val timestamp: Long?,
    @Json(name = "base") val base: String?,
    @Json(name = "date") val date: String?,
    @Json(name = "rates") val rates: Map<String,Double>?
)
