package com.app.codehero.di

import com.app.codehero.domain.usecase.ListCharactersUseCase
import com.app.codehero.domain.usecase.ListCharactersUseCaseImpl
import com.app.codehero.ui.main.CharacterDetailsViewModel
import com.app.codehero.ui.main.ListCharacterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        ListCharacterViewModel(
            listCharactersUseCase = get()
        )
    }

    viewModel {
        CharacterDetailsViewModel(
            characterDetailsUseCase = get()
        )
    }
}