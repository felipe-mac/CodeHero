package com.app.codehero.domain.usecase

import com.app.codehero.api.Result
import com.app.codehero.data.CharRepository
import com.app.codehero.domain.model.Character
import com.app.codehero.domain.model.Characters
import javax.inject.Inject

class CharacterDetailsUseCaseImpl @Inject constructor(
    private val charRepository: CharRepository
): CharacterDetailsUseCase {

    override suspend fun invoke(charId: Int): Result<Characters?> {
        return charRepository.getCharacterDetails(charId)
    }
}