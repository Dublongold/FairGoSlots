package com.fortheworthy.game.fairgoslots.viewModelPart.fragments.loading

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fortheworthy.game.fairgoslots.modelPart.FetchedData
import com.fortheworthy.game.fairgoslots.networkPart.FetchingDataClient
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LoadingViewModel: ViewModel() {
    fun fetchData(callback: suspend (FetchedData?) -> Unit) {
        viewModelScope.launch {
            callback(FetchingDataClient.fetch())
        }
    }
}