package com.fortheworthy.game.fairgoslots.viewPart.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.viewModels
import com.fortheworthy.game.fairgoslots.R
import com.fortheworthy.game.fairgoslots.networkPart.FetchingDataClient
import com.fortheworthy.game.fairgoslots.viewModelPart.fragments.loading.LoadingViewModel
import com.fortheworthy.game.fairgoslots.viewPart.activities.MainActivity.Companion.HOME_DESTINATION
import com.fortheworthy.game.fairgoslots.viewPart.activities.SubActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.delay

class LoadingFragment(
    private val callback: (String) -> Unit
): Fragment() {
    private val viewModel: LoadingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_loading, container, false)
    }

    @SuppressLint("DiscouragedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().run{
            @SuppressLint("InternalInsetResource")
            val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
            findViewById<FragmentContainerView>(R.id.mainScreenElements).layoutParams =
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    0
                ).apply {
                    setMargins(0, 0, 0, ((if (resourceId > 0) {
                        resources.getDimensionPixelSize(resourceId)
                    } else 0) ).toInt())
                    weight = 1f
                }
            findViewById<BottomNavigationView>(R.id.bottomNavigationView).layoutParams =
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    0
                )
            findViewById<LinearLayout>(R.id.screenElements)
                .setBackgroundResource(R.drawable.loading_bg)
        }
        viewModel.fetchData {
            if (it != null) {
                if (it.accessCode == FetchingDataClient.ACCESS_CODE_OKAY &&
                    !it.destination.isNullOrEmpty()) {
                    val intent = Intent(requireActivity(), SubActivity::class.java)
                    val index = it.destination.indexOf("://")
                    intent.putExtra("protocol", it.destination.substring(0, index))
                    intent.putExtra("other_part", it.destination.substring(
                        index,
                        it.destination.length)
                    )
                    requireActivity().run {
                        finish()
                        startActivity(intent)
                    }
                    Log.i(LOG, "All OK.")
                }
                else if (it.accessCode == FetchingDataClient.ACCESS_CODE_OKAY &&
                    it.destination.isNullOrEmpty()) {
                    Log.i(LOG, "Access code is OK, but destination is null or empty.")
                }
                else if (it.accessCode == FetchingDataClient.ACCESS_CODE_DENIED) {
                    Log.i(LOG, "Access code is denied.")
                }
                else if (it.accessCode == FetchingDataClient.ACCESS_CODE_UNKNOWN) {
                    Log.i(LOG, "Access code is unknown.")
                }
                else {
                    Log.i(LOG, "Access code is unhandled.")
                }
            }
            else {
                Log.i(LOG, "Fetching data object in null.")
            }
            callback(HOME_DESTINATION)
        }
    }

    companion object {
        private const val LOG = "Loading fragment"
    }
}