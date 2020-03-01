package com.android.omise.di

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
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

    @Provides
    @Singleton
    fun providesSharedPreferences(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }


}