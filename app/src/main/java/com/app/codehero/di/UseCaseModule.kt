package com.app.codehero.di

import com.app.codehero.domain.usecase.CharacterDetailsUseCase
import com.app.codehero.domain.usecase.CharacterDetailsUseCaseImpl
import com.app.codehero.domain.usecase.ListCharactersUseCase
import com.app.codehero.domain.usecase.ListCharactersUseCaseImpl
import org.koin.dsl.module

val useCaseModule = module {

    factory<ListCharactersUseCase> {
        ListCharactersUseCaseImpl(
            charRepository = get()
        )
    }

    factory<CharacterDetailsUseCase> {
        CharacterDetailsUseCaseImpl(
            charRepository = get()
        )
    }
}