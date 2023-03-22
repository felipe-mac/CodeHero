package com.app.codehero.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.codehero.di.annotations.ViewModelKey
import com.app.codehero.domain.usecase.ListCharactersUseCase
import com.app.codehero.domain.usecase.ListCharactersUseCaseImpl
import com.app.codehero.ui.main.CharacterDetailsViewModel
import com.app.codehero.ui.main.ListCharacterViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ListCharacterViewModel::class)
    abstract fun bindListCharacterViewModel(viewModel: ListCharacterViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CharacterDetailsViewModel::class)
    abstract fun bindCharacterDetailsViewModel(viewModel: CharacterDetailsViewModel): ViewModel

}