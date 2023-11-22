package com.revappcasi.nofair.goplay.viewModelPart.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.webkit.ValueCallback
import android.webkit.WebSettings
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException

class SubViewModel: ViewModel() {
    private val changeableMainValueCallback: MutableStateFlow<ValueCallback<Array<Uri>>?> =
        MutableStateFlow(null)
    private val changeableUri: MutableStateFlow<Uri?> =
        MutableStateFlow(null)

    private val uri: StateFlow<Uri?>
        get() = changeableUri
    val mainValueCallback: StateFlow<ValueCallback<Array<Uri>>?>
        get() = changeableMainValueCallback

    fun setUri(newUri: Uri?) {
        changeableUri.value = newUri
    }

    fun makeMainValueCallbackNull() {
        changeableMainValueCallback.value = null
    }

    fun setMainValueCallback(valueCallback: ValueCallback<Array<Uri>>) {
        changeableMainValueCallback.value = valueCallback
    }

    fun onReceiveValueForMainValueCallback(data: Intent?) {
        mainValueCallback.value?.run {
            val dataString = data?.dataString
            if (data != null && dataString != null) {
                val u = Uri.parse(dataString)
                onReceiveValue(arrayOf(u))
            } else {
                privateOnReceiveValueForMainValueCallback()
            }
        }
    }
    private val mixedContentAlwaysAllow = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
    private val loadDefault = WebSettings.LOAD_DEFAULT

    fun setSettingsOfSubView(settings: WebSettings) {
        settings.mixedContentMode = mixedContentAlwaysAllow
        settings.cacheMode = loadDefault
        setBooleans(settings)
        settings.userAgentString =
            settings.userAgentString.replace("; wv", "")
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setBooleans(settings: WebSettings) {
        settings.domStorageEnabled = true
        settings.javaScriptEnabled = true
        settings.databaseEnabled = settings.domStorageEnabled
        @Suppress("DEPRECATION")
        settings.allowUniversalAccessFromFileURLs = true
        settings.useWideViewPort = settings.databaseEnabled
        settings.loadWithOverviewMode = true

        settings.allowContentAccess = settings.domStorageEnabled
        settings.allowFileAccess = settings.databaseEnabled
        settings.javaScriptCanOpenWindowsAutomatically = true
        @Suppress("DEPRECATION")
        settings.allowFileAccessFromFileURLs = settings.domStorageEnabled
    }

    private fun ValueCallback<Array<Uri>>.privateOnReceiveValueForMainValueCallback() {
        val uriValue = uri.value
        if (uriValue != null) {
            onReceiveValue(arrayOf(uriValue))
        } else {
            onReceiveValue(null)
        }
    }

    fun onChoiceFile(externalFilesDir: File?, action: suspend (File?) -> Unit) {
        viewModelScope.launch (Dispatchers.IO) {
            var file: File? = null
            var doCycle = true
            launch {
                while (file == null && doCycle) {
                    delay(100)
                }
            }
            file = try {
                doCycle = false
                doCycle = true
                File.createTempFile(
                    "file",
                    ".jpg",
                    externalFilesDir
                )
            } catch (badThing: IOException) {
                Log.e(
                    "PhotoFile",
                    "Unable to create this piece of useless bytes.",
                    badThing
                )
                doCycle = false
                null
            }
            action(file)
        }
    }
}