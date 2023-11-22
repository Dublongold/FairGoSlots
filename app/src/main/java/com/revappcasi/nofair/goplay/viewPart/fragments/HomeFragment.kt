package com.revappcasi.nofair.goplay.viewPart.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.revappcasi.nofair.goplay.R
import com.revappcasi.nofair.goplay.instruments.recyclerView.MainAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeFragment(
    private val callback: (String, Int) -> Unit
): Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
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
            (view as? ViewGroup)?.addView(View(context).apply {
                layoutParams = ConstraintLayout.LayoutParams(
                    0, 0
                ).apply {
                    topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                    startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                }
                setBackgroundColor(Color.TRANSPARENT)
            })
        }

        view.run{
            findViewById<RecyclerView>(R.id.articles).run {
                adapter = MainAdapter(callback)
                layoutManager = LinearLayoutManager(context)
            }
        }

    }
}