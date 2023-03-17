package com.app.codehero.di

import com.app.codehero.data.CharRepository
import com.app.codehero.data.CharacterDataSource
import com.app.codehero.data.RetrofitCharacterDataSource
import org.koin.dsl.module

val repositoryModule = module {

    factory<CharacterDataSource> {
        RetrofitCharacterDataSource()
    }

    single {
        CharRepository(dataSource = get())
    }
}