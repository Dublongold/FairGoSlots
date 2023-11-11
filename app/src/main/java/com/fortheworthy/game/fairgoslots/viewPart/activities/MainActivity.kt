package com.fortheworthy.game.fairgoslots.viewPart.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.fortheworthy.game.fairgoslots.R
import com.fortheworthy.game.fairgoslots.viewPart.fragments.ArticleFragment
import com.fortheworthy.game.fairgoslots.viewPart.fragments.HomeFragment
import com.fortheworthy.game.fairgoslots.viewPart.fragments.LoadingFragment
import com.fortheworthy.game.fairgoslots.viewPart.fragments.PrivacyPolicyFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    @SuppressLint("DiscouragedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        supportFragmentManager.beginTransaction()
            .add(R.id.mainScreenElements, LoadingFragment(::navigate))
            .commit()
        findViewById<BottomNavigationView>(R.id.bottomNavigationView).setOnItemSelectedListener {
            navigate(if (it.title == getString(R.string.home)) {
                HOME_DESTINATION
            }
            else {
                PRIVACY_POLICY_DESTINATION
            }, null)
            true
        }
    }
    private fun navigate(destination: String) {
        navigate(destination, null)
    }
    private fun navigate(destination: String, argument: Int?) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainScreenElements, when(destination) {
                LOADING_DESTINATION -> LoadingFragment(::navigate)
                HOME_DESTINATION -> HomeFragment(::navigate)
                ARTICLE_DESTINATION -> ArticleFragment(::navigate, argument ?: -1)
                PRIVACY_POLICY_DESTINATION -> PrivacyPolicyFragment(::navigate)
                else -> Fragment()
            })
            .commitAllowingStateLoss()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == 4) {
            val fragment = supportFragmentManager.fragments.firstOrNull()
            if (fragment != null && fragment is ArticleFragment) {
                navigate(HOME_DESTINATION)
                true
            }
            else {
                finish()
                true
            }
        }
        else false
    }

    companion object {
        const val LOADING_DESTINATION = "loading"
        const val HOME_DESTINATION = "home"
        const val PRIVACY_POLICY_DESTINATION = "privacy policy"
        const val ARTICLE_DESTINATION = "article"
    }
}