package com.fortheworthy.game.fairgoslots.viewPart.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import com.fortheworthy.game.fairgoslots.R
import com.fortheworthy.game.fairgoslots.viewPart.activities.MainActivity.Companion.HOME_DESTINATION
import com.fortheworthy.game.fairgoslots.viewPart.compose.TextWithGoldBorder
import com.google.android.material.bottomnavigation.BottomNavigationView

class ArticleFragment(
    private val callback: (String) -> Unit,
    private val articleId: Int
): Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_article, container, false)
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
                    } else 0), 0, (20 / resources.displayMetrics.density).toInt())
                    weight = 1f
                }
            findViewById<BottomNavigationView>(R.id.bottomNavigationView).run {
                layoutParams =
                    LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0)
            }
            findViewById<LinearLayout>(R.id.screenElements)
                .setBackgroundResource(R.drawable.all_bg)
        }
        view.run {
            findViewById<ImageButton>(R.id.backButton).setOnClickListener {
                callback(HOME_DESTINATION)
            }
            findViewById<TextView>(R.id.articleTitle).text = resources
                .getStringArray(R.array.article_titles)[articleId]
            findViewById<ComposeView>(R.id.articleContent).setContent {
                TextWithGoldBorder(
                    stringArrayResource(id = R.array.article_content)[articleId],
                    false
                )
            }
            findViewById<ImageView>(R.id.articleImage).setImageResource(
                R.drawable.img01 + articleId
            )
        }
    }
}