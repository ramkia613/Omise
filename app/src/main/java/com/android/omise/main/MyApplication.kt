package com.android.omise.main

import com.android.omise.data.usecase.UseCase
import com.android.omise.di.AndroidAppComponent
import com.android.omise.di.DaggerAndroidAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import kotlinx.coroutines.Dispatchers

open class MyApplication : DaggerApplication() {

    private lateinit var component: AndroidAppComponent

    override fun onCreate() {
        super.onCreate()
        UseCase.dispatcher = Dispatchers.IO
    }

    override fun applicationInjector(): AndroidInjector<out MyApplication> {
        component = DaggerAndroidAppComponent.factory().create(this) as AndroidAppComponent
        return component
    }
}