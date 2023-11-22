package com.revappcasi.nofair.goplay.networkPart

import com.revappcasi.nofair.goplay.modelPart.FetchedData
import retrofit2.Response
import retrofit2.http.GET

interface FetchingDataAPI {
    @GET("1qXBwzfm")
    suspend fun fetch(): Response<FetchedData>
}