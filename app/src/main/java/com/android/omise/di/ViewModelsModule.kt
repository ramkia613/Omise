package com.android.omise.di

import androidx.lifecycle.ViewModel
import com.android.omise.charity.CharityViewModel
import com.android.omise.donation.DonationViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(CharityViewModel::class)
    internal abstract fun bindCharityViewModel(model: CharityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DonationViewModel::class)
    internal abstract fun bindDonationViewModel(model: DonationViewModel): ViewModel

}