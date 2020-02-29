package com.android.omise.di

import com.android.omise.main.MyApplication
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class, BindsModule::class, AndroidModule::class, ViewModelsModule::class, AppModule::class, ServiceModule::class]
)
interface AndroidAppComponent : AndroidInjector<MyApplication> {

    @Component.Factory
    abstract class Builder : AndroidInjector.Factory<MyApplication>
}