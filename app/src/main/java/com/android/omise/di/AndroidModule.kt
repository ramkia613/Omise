package com.android.omise.di

import com.android.omise.charity.CharityFragment
import com.android.omise.donation.DonationFragment
import com.android.omise.donation.SuccessFragment
import com.android.omise.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class AndroidModule {
    
    @ContributesAndroidInjector
    internal abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    internal abstract fun bindCharityFragment(): CharityFragment

    @ContributesAndroidInjector
    internal abstract fun bindDonationFragment(): DonationFragment

    @ContributesAndroidInjector
    internal abstract fun bindSuccessFragmentt(): SuccessFragment
}