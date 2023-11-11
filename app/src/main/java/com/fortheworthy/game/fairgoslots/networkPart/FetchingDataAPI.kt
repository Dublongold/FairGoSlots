package com.fortheworthy.game.fairgoslots.networkPart

import com.fortheworthy.game.fairgoslots.modelPart.FetchedData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface FetchingDataAPI {
    @GET("1qXBwzfm")
    suspend fun fetch(): Response<FetchedData>
}