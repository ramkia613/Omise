package com.android.omise.di

import com.android.omise.charity.CharityFragment
import com.android.omise.main.MainActivity
import com.android.omise.main.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class AndroidModule {

    @ContributesAndroidInjector
    internal abstract fun bindSplashActivity(): SplashActivity

    @ContributesAndroidInjector
    internal abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    internal abstract fun bindCharityFragment(): CharityFragment
}