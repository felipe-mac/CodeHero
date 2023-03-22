package com.app.codehero.di

import com.app.codehero.data.CharRepository
import com.app.codehero.data.CharacterDataSource
import com.app.codehero.data.RetrofitCharacterDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun provideCharRepository(dataSource: CharacterDataSource): CharRepository = CharRepository(dataSource)
}

