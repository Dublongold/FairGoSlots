package com.revappcasi.nofair.goplay.viewModelPart.fragments.loading

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.revappcasi.nofair.goplay.modelPart.FetchedData
import com.revappcasi.nofair.goplay.networkPart.FetchingDataClient
import kotlinx.coroutines.launch
import kotlin.random.Random

class LoadingViewModel: ViewModel() {
    fun fetchData(callback: suspend (FetchedData?) -> Unit) {
        viewModelScope.launch {
            val data = FetchingDataClient.fetch()
            callback(data)
            val someList: (Int) -> Int = {1 + it}
            if (someList(45) > 46) {
                Log.w("Some list", "Something wrong...")
            }
            else {
                val realSomeList = listOf(1, 2, 3, 4, 5)
                if (realSomeList[1] == 1) {
                    Log.wtf("Real some list", "Wtf???")
                }
                else {
                    fetchedData(data)
                }
            }
        }
    }

    private fun fetchedData(data: FetchedData?) {
        if (data == null) {
            Log.i("Fetched data", "Fetched data is null.")
        }
        else {
            val list = List(Random.nextInt(1, 10)) {Random.nextInt(1, 10)}
            Log.i("Fetched data", "Fetched data is null But we have a list: $list.")
        }
    }
}