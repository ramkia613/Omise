package com.android.omise.main

import android.os.Build
import com.android.omise.BuildConfig
import com.android.omise.data.usecase.UseCase
import com.android.omise.di.AndroidAppComponent
import com.android.omise.di.DaggerAndroidAppComponent
import com.android.omise.util.EncryptionUtil
import com.facebook.stetho.Kryptoscope
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import kotlinx.coroutines.Dispatchers
import java.security.KeyStore
import java.security.PrivateKey

open class MyApplication : DaggerApplication() {

    private lateinit var component: AndroidAppComponent

    override fun onCreate() {
        super.onCreate()
        UseCase.dispatcher = Dispatchers.IO
        initPlugins()
    }

    override fun applicationInjector(): AndroidInjector<out MyApplication> {
        component = DaggerAndroidAppComponent.factory().create(this) as AndroidAppComponent
        return component
    }

    protected open fun initPlugins() {
        if (BuildConfig.DEBUG) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                Kryptoscope.initializeWithDefaults(
                    this,
                    (EncryptionUtil(applicationContext).getKey() as PrivateKey).toString()
                )
            } else {
                Kryptoscope.initializeWithDefaults(
                    this,
                    (EncryptionUtil(applicationContext).getKey() as KeyStore.PrivateKeyEntry).toString()
                )
            }
        }
    }
}