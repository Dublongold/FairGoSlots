package com.fortheworthy.game.fairgoslots.viewPart.activities

import android.Manifest;
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.KeyEvent
import android.webkit.CookieManager
import android.webkit.JsPromptResult
import android.webkit.JsResult
import android.webkit.RenderProcessGoneDetail
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.fortheworthy.game.fairgoslots.R
import com.fortheworthy.game.fairgoslots.viewModelPart.activities.SubViewModel


class SubActivity : AppCompatActivity() {
    private val viewModel: SubViewModel by viewModels()

    private lateinit var subView: WebView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub)
        subView = findViewById(R.id.subView)
        var url = ""
        // If url is http://some.host/some/path :
        url += intent.getStringExtra("protocol") // "http"
        url += intent.getStringExtra("other_part") // "://some.host/some/path"
        Log.i("Sub activity", url)
        setSettingsOfSubViewSettings()
        subView.loadUrl(url)
    }

    private fun setCookies(cookieManager: CookieManager) {
        cookieManager.setAcceptCookie(true)
        cookieManager.setAcceptThirdPartyCookies(subView, true)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setSettingsOfSubViewSettings() {
        setCookies(CookieManager.getInstance())
        viewModel.setSettingsOfSubView(subView.settings)
        subView.webChromeClient = WCClient()
        subView.webViewClient = WVClient()
    }

    inner class WVClient : WebViewClient() {

        private var lastLoadedResource: String? = null

        override fun onLoadResource(view: WebView?, url: String?) {
            super.onLoadResource(view, url)
            lastLoadedResource = url
            Log.i("Client", "Load $lastLoadedResource")
        }

        override fun onReceivedLoginRequest(
            view: WebView?,
            realm: String?,
            account: String?,
            args: String?
        ) {
            if (view == null) {
                Log.w("WVClient", "Why sub view is null...?")
            }
            else {
                Log.i("WVClient", "[onReceivedLoginRequest] " +
                        "certificate: ${view.certificate}")
            }
            if (args != null) {
                Log.i("WVClient", args)
            }
            super.onReceivedLoginRequest(view, realm, account, args)
        }

        override fun onRenderProcessGone(
            view: WebView?,
            detail: RenderProcessGoneDetail?
        ): Boolean {
            Log.i("WVClient", "[onRenderProcessGone] $detail")
            return super.onRenderProcessGone(view, detail)
        }

        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            val uri = request.url.toString()
            return if (uri.contains("/")) {
                Log.e("Uri", uri)
                if (uri.contains("http")) {
                    false
                } else {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(uri)))
                    true
                }
            } else true
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            Log.i("WVClient", "[onP{ageFinished] $url.")
        }
    }

    inner class WCClient : WebChromeClient() {
        private var webView: WebView? = null

        override fun onShowFileChooser(
            webView: WebView,
            filePathCallback: ValueCallback<Array<Uri>>,
            fileChooserParams: FileChooserParams
        ): Boolean {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            viewModel.setMainValueCallback(filePathCallback)
            return true
        }

        override fun onRequestFocus(view: WebView?) {
            super.onRequestFocus(view)
            webView = view
            Log.i("WCClient", "[onRequestFocus] $view")
        }

        override fun onJsAlert(
            view: WebView?,
            url: String?,
            message: String?,
            result: JsResult?
        ): Boolean {
            if (view != null) {
                Log.i(
                    "WCClient", "[onJsAlert] url: $url, message: $message, " +
                            "result: $result."
                )
            }
            else {
                Log.wtf("WCClient", "Why view is null?")
            }
            return super.onJsAlert(view, url, message, result)
        }

        override fun onJsPrompt(
            view: WebView?,
            url: String?,
            message: String?,
            defaultValue: String?,
            result: JsPromptResult?
        ): Boolean {
            Log.i("WCClient", "Some js prompt.")

            val viewNull = view == null
            val urlNull = url == null
            val messageNull = message == null
            val defaultValueNull = defaultValue == null
            val resultNull = result == null

            if (viewNull || urlNull || messageNull || defaultValueNull || resultNull) {
                Log.w("WCClient", "Something is null... $viewNull $urlNull " +
                        "$messageNull $defaultValueNull $resultNull")
            }
            return super.onJsPrompt(view, url, message, defaultValue, result)
        }
    }

    val requestPermissionLauncher = registerForActivityResult<String, Boolean>(
        ActivityResultContracts.RequestPermission()
    ) {
        viewModel.onChoiceFile(getExternalFilesDir(Environment.DIRECTORY_PICTURES)) {
                photoFile ->
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
                putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile))
            }

            viewModel.setUri(Uri.fromFile(photoFile))

            val old = Intent(Intent.ACTION_GET_CONTENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "*/*"
            }

            val intentArray = arrayOf(takePictureIntent)

            val chooserIntent = Intent(Intent.ACTION_CHOOSER).apply {
                putExtra(Intent.EXTRA_INTENT, old)
                putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray)
            }

            intentLauncher.launch(chooserIntent)
        }
    }

    private val intentLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        val (resultCode, data) = it.resultCode to it.data
        val mFilePathCallback = viewModel.mainValueCallback.value
            ?: return@registerForActivityResult
        if (resultCode == -1) {
            viewModel.onReceiveValueForMainValueCallback(data)
        } else {
            mFilePathCallback.onReceiveValue(null)
        }
        viewModel.makeMainValueCallbackNull()
    }

    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        subView.saveState(outState)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == 4 && subView.canGoBack()) {
            subView.goBack()
            return true
        }
        return false
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        subView.restoreState(savedInstanceState)
    }
}