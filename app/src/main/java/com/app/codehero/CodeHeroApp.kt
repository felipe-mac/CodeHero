package com.app.codehero

import android.app.Application
import com.app.codehero.di.ApplicationComponent
import com.app.codehero.di.DaggerApplicationComponent

class CodeHeroApp: Application() {

    lateinit var appComponent: ApplicationComponent
    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerApplicationComponent.factory().create(this)
    }
}