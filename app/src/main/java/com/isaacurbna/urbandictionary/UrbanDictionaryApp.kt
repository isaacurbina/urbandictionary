package com.isaacurbna.urbandictionary

import android.app.Application
import com.isaacurbna.urbandictionary.dagger.component.AppComponent
import com.isaacurbna.urbandictionary.dagger.component.DaggerAppComponent
import com.isaacurbna.urbandictionary.dagger.module.AppModule

class UrbanDictionaryApp : Application() {

    companion object {
        lateinit var applicationComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}