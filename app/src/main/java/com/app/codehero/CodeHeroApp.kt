package com.app.codehero

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CodeHeroApp: Application() {

//    lateinit var appComponent: ApplicationComponent
    override fun onCreate() {
        super.onCreate()

//        appComponent = DaggerApplicationComponent.factory().create(this)
    }
}