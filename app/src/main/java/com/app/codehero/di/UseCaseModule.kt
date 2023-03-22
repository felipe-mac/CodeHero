package com.app.codehero.di

import com.app.codehero.data.CharRepository
import com.app.codehero.domain.usecase.CharacterDetailsUseCase
import com.app.codehero.domain.usecase.CharacterDetailsUseCaseImpl
import com.app.codehero.domain.usecase.ListCharactersUseCase
import com.app.codehero.domain.usecase.ListCharactersUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {

    @Binds
    fun bindListCharactersUseCase(useCase: ListCharactersUseCaseImpl): ListCharactersUseCase

    @Binds
    fun bindCharacterDetailsUseCase(useCase: CharacterDetailsUseCaseImpl): CharacterDetailsUseCase
}