package com.app.codehero.domain.usecase

import com.app.codehero.api.Result
import com.app.codehero.data.CharRepository
import com.app.codehero.domain.model.Characters

class ListCharactersUseCaseImpl(
    private val charRepository: CharRepository
): ListCharactersUseCase {

    override suspend fun invoke(name: String, offset: Int, count: Int): Result<Characters?> {
        return charRepository.getChars(name, offset, count)
    }

}