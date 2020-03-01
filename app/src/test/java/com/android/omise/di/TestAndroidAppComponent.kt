package com.android.omise.di

import com.android.omise.main.MyApplication
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class, BindsModule::class, TestAppModule::class, ViewModelsModule::class]
)
interface TestAndroidAppComponent : AndroidAppComponent {

    @Component.Factory
    abstract class Builder : AndroidInjector.Factory<MyApplication>


}
