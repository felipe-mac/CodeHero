package com.app.codehero.di

import com.app.codehero.data.CharRepository
import com.app.codehero.domain.usecase.CharacterDetailsUseCase
import com.app.codehero.domain.usecase.CharacterDetailsUseCaseImpl
import com.app.codehero.domain.usecase.ListCharactersUseCase
import com.app.codehero.domain.usecase.ListCharactersUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {

    @Provides
    fun provideListCharactersUseCase(repository: CharRepository): ListCharactersUseCase {
        return ListCharactersUseCaseImpl(repository)
    }

    @Provides
    fun provideCharacterDetailsUseCase(repository: CharRepository): CharacterDetailsUseCase {
        return CharacterDetailsUseCaseImpl(repository)
    }
}