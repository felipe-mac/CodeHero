package com.app.codehero.data

import com.app.codehero.api.Result
import com.app.codehero.domain.model.Characters
import javax.inject.Inject

class CharRepository @Inject constructor (
    private val dataSource: CharacterDataSource
) {

    suspend fun getChars(name: String, offset: Int, count: Int): Result<Characters?> =
        dataSource.getChars(name, offset, count)

    suspend fun getCharacterDetails(characterId: Int): Result<Characters?> =
        dataSource.getCharacterDetails(characterId)
}