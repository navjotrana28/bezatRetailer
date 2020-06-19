package com.bezatretailernew.bezat.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bezatretailernew.bezat.R
import com.bezatretailernew.bezat.utils.SharedPrefs
import com.bezatretailernew.bezat.utils.URLS
import com.github.kittinunf.fuel.core.FuelManager
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (SharedPrefs.getKey(this, "selectedlanguage").contains("ar")) {
            window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL
            setLocale("ar")

        } else {
            window.decorView.layoutDirection = View.LAYOUT_DIRECTION_LTR
            setLocale("en")

        }
        setContentView(R.layout.activity_main);
        supportActionBar?.hide();
        Handler(Looper.getMainLooper()).postDelayed({
            if (SharedPrefs.getKey(this@MainActivity, "LoggedIn").equals("true")) {
                startActivity(Intent(this, Homepage::class.java))
            } else {
                startActivity(Intent(this, Intro::class.java))

            }
            finish()
        }, 3000)
        FuelManager.instance.apply {
            basePath = URLS.BASE_PATH
            baseHeaders = mapOf("apiKey" to "12345678")
        }
    }

    fun setLocale(lang: String) {
        SharedPrefs.setKey(this, "selectedlanguage", lang)
        val myLocale = Locale(lang)
        val res = resources
        val dm = res.displayMetrics
        val conf = res.configuration
        conf.locale = myLocale
        res.updateConfiguration(conf, dm)
    }
}
