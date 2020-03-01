package com.android.omise.main

import android.content.SharedPreferences
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.android.omise.R
import com.android.omise.util.EncryptionUtil
import com.android.omise.util.setEncryptedString
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

const val PUBLIC_KEY = "public_key"

class MainActivity : BaseActivity() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    @Inject
    lateinit var encryptionUtil: EncryptionUtil

    override fun getLayoutResourceId() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupActionBarWithNavController((navHostFragment as NavHostFragment).navController)
        sharedPreferences.setEncryptedString(PUBLIC_KEY, getPublicKey(), encryptionUtil)
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.navHostFragment).navigateUp()
    }

    init {
        System.loadLibrary("native-lib")
    }

    external fun getPublicKey(): String

}

