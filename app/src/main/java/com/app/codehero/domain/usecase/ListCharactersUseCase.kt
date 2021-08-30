package com.app.codehero.domain.usecase

import com.app.codehero.api.Result
import com.app.codehero.domain.model.Characters


interface ListCharactersUseCase {

    suspend operator fun invoke(name: String, offset: Int, count: Int): Result<Characters?>
}