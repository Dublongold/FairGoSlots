package com.fortheworthy.game.fairgoslots.viewPart.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import com.fortheworthy.game.fairgoslots.R
import com.fortheworthy.game.fairgoslots.viewPart.compose.TextWithGoldBorder
import com.google.android.material.bottomnavigation.BottomNavigationView

class PrivacyPolicyFragment(
    private val callback: (String) -> Unit
): Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_privacy_policy, container, false)
    }

    @SuppressLint("DiscouragedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().run{
            @SuppressLint("InternalInsetResource")
            val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
            @SuppressLint("InternalInsetResource")
            val resourceIdToo = resources.getIdentifier("status_bar_height", "dimen", "android")
            findViewById<FragmentContainerView>(R.id.mainScreenElements).layoutParams =
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    0
                ).apply {
                    setMargins(0, (if (resourceIdToo > 0) {
                        resources.getDimensionPixelSize(resourceIdToo)
                    } else 0), 0, 0,)
                    weight = 1f
                }
            findViewById<BottomNavigationView>(R.id.bottomNavigationView).run {
                layoutParams =
                    LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                setPadding(0, 0, 0, ((if (resourceId > 0) {
                    resources.getDimensionPixelSize(resourceId)
                } else 0) ).toInt())
            }
            findViewById<LinearLayout>(R.id.screenElements)
                .setBackgroundResource(R.drawable.all_bg)
        }
        if (isDetached) {
            callback("")
        }

        view.findViewById<ComposeView>(R.id.privacyPolicyText).setContent {
            Text(text = "", style = TextStyle(color = Color.Transparent, fontSize = 0.sp))
            TextWithGoldBorder(R.string.privacy_policy_text)
        }
    }
}