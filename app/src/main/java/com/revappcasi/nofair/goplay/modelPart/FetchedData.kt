package com.revappcasi.nofair.goplay.modelPart

import android.util.Log

data class FetchedData(
    val accessCode: Int,
    val destination: String?
) {
    init {
        checkData()
    }
    private fun checkData() {
        if (accessCode == 200) {
            val usefulList = listOf(accessCode, accessCode / 2, accessCode - 2)
            if (destination != null) {
                val stringList = mutableListOf(
                    destination.substring(0, destination.length / 2),
                    destination.substring(destination.length / 2, destination.length)
                )
                if (usefulList[1] == accessCode / 2) {
                    stringList.add("?")
                }
                if (stringList.size == 3) {
                    Log.i("Fetched data", "Good data: code: $accessCode, " +
                            "destination: $destination")
                }
            }
            else {
                Log.i("Fetched data", "Destination is null...")
            }
        }
        else {
            Log.i("Fetched data", "Code is bad...")
        }
    }
}
