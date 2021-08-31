package com.app.codehero.domain.usecase

import com.app.codehero.api.Result
import com.app.codehero.data.CharacterRepository
import com.app.codehero.domain.model.Characters

class CharacterDetailsUseCaseImpl(
    private val characterRepository: CharacterRepository
): CharacterDetailsUseCase {

    override suspend fun invoke(charId: Int): Result<Characters?> {
        return characterRepository.getCharacterDetails(charId)
    }
}