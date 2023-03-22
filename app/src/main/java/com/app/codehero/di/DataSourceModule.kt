package com.app.codehero.di

import com.app.codehero.data.CharacterDataSource
import com.app.codehero.data.RetrofitCharacterDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {
    @Singleton
    @Binds
    fun bindsRetrofitDataSource(dataSource: RetrofitCharacterDataSource): CharacterDataSource
}