package com.isaacurbna.urbandictionary.dagger.component

import android.content.Context
import com.isaacurbna.urbandictionary.MainActivity
import com.isaacurbna.urbandictionary.dagger.module.AppModule
import com.isaacurbna.urbandictionary.dagger.module.IOModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, IOModule::class])
interface AppComponent {

    fun inject(mainActivity: MainActivity)

    /* Expose objects created by modules to any other components
       dependent on this component. */
    fun context(): Context

}