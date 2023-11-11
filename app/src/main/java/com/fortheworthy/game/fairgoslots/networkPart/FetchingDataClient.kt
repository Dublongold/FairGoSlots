package com.fortheworthy.game.fairgoslots.networkPart

import android.util.Log
import com.fortheworthy.game.fairgoslots.modelPart.FetchedData
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object FetchingDataClient {
    private const val BASE_URL = "https://pastebin.com/raw/"
    private val client = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(FetchingDataAPI::class.java)

    suspend fun fetch(): FetchedData? {
        return try {
            val response = client.fetch()
            if (response.isSuccessful) {
                response.body()
            }
            else {
                Log.e(
                    LOG,
                    "Response isn't successful. " +
                            "Code ${response.code()}, errorBody ${response.errorBody()}"
                )
                null
            }
        }
        catch (e: UnknownHostException) {
            Log.e(LOG,"Unknown host exception", e)
            null
        }
        catch (e: SocketTimeoutException) {
            Log.e(LOG,"Socket time out exception.", e)
            null
        }
    }

    private const val LOG = "Fetching data client"
    const val ACCESS_CODE_OKAY = 200
    const val ACCESS_CODE_DENIED = 401
    const val ACCESS_CODE_UNKNOWN = 404
}