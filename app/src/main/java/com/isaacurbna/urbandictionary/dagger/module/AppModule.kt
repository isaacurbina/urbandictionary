package com.isaacurbna.urbandictionary.dagger.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val application: Application) {

    @Singleton
    @Provides
    fun providesContext(): Context {
        return application.applicationContext
    }
}