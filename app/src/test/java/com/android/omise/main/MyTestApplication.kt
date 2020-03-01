package com.android.omise.main

import com.android.omise.data.usecase.UseCase
import com.android.omise.di.AndroidAppComponent
import com.android.omise.di.DaggerAndroidAppComponent
import dagger.android.AndroidInjector
import kotlinx.coroutines.Dispatchers.IO

open class MyTestApplication : MyApplication() {

    lateinit var component: AndroidAppComponent

    override fun onCreate() {
        super.onCreate()
        UseCase.dispatcher = IO
    }

    override fun applicationInjector(): AndroidInjector<out MyApplication> {
        component = DaggerAndroidAppComponent.factory().create(this) as AndroidAppComponent
        return component
    }

    override fun initPlugins() {
        // not initialising any plugin for test
    }
}