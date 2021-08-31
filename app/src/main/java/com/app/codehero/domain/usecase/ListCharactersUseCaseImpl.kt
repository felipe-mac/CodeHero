package com.app.codehero.domain.usecase

import com.app.codehero.api.Result
import com.app.codehero.data.CharacterRepository
import com.app.codehero.domain.model.Characters

class ListCharactersUseCaseImpl(
    private val characterRepository: CharacterRepository
): ListCharactersUseCase {

    override suspend fun invoke(name: String, offset: Int, count: Int): Result<Characters?> {
        return characterRepository.getCharacters(name, offset, count)
    }

}