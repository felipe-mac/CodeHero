package com.app.codehero.di

import com.app.codehero.data.CharacterDataSource
import com.app.codehero.data.RetrofitCharacterDataSource
import dagger.Module
import dagger.Provides

@Module
class DataSourceModule {
    @Provides
    fun provideRetrofitDataSource(): CharacterDataSource {
        return RetrofitCharacterDataSource()
    }
}