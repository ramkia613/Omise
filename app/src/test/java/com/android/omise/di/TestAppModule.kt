package com.android.omise.di

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.android.omise.helper.shadow.EncryptionTestUtil
import com.android.omise.main.MyApplication
import com.android.omise.util.EncryptionUtil
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * We using the same module for now, but maybe replace few things in future for our tests
 */
@Module(includes = [AndroidModule::class, ServiceModule::class])
class TestAppModule() {

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

    @Provides
    @Singleton
    fun providesEncryptionUtil(context: Context): EncryptionUtil {
        return EncryptionTestUtil(context)
    }

}