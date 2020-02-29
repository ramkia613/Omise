package com.android.omise.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.android.omise.R

class SplashActivity : BaseActivity() {

    override fun getLayoutResourceId() = R.layout.activity_splash

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler().postDelayed({
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }, 3000)
    }

}
