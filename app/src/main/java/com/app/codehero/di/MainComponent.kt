package com.app.codehero.di

import com.app.codehero.MainActivity
import com.app.codehero.ui.main.CharacterDetailsActivity
import dagger.Subcomponent

@Subcomponent(modules = [ViewModelModule::class])
interface MainComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): MainComponent
    }

    fun inject(activity: MainActivity)
    fun inject(activity: CharacterDetailsActivity)

}