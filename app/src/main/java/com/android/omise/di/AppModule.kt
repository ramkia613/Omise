package com.android.omise.di

import android.content.Context
import android.content.SharedPreferences
import com.android.omise.main.MyApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    fun providesContext(application: MyApplication): Context {
        return application.applicationContext
    }

}