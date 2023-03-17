package com.app.codehero

import android.app.Application
import com.app.codehero.di.viewModelModule
import com.app.codehero.di.repositoryModule
import com.app.codehero.di.useCaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CodeHeroApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@CodeHeroApp)
            modules(
                viewModelModule,
                repositoryModule,
                useCaseModule
            )
        }
    }
}