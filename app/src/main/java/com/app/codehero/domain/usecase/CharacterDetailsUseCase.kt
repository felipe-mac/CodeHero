package com.app.codehero.domain.usecase

import com.app.codehero.api.Result
import com.app.codehero.domain.model.Character
import com.app.codehero.domain.model.Characters

interface CharacterDetailsUseCase {

    suspend operator fun invoke(charId:Int): Result<Characters?>
}