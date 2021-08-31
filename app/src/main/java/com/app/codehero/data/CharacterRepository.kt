package com.app.codehero.data

import com.app.codehero.api.Result
import com.app.codehero.domain.model.Characters

class CharacterRepository(private val dataSource: CharacterDataSource) {

    suspend fun getCharacters(name: String, offset: Int, count: Int): Result<Characters?> =
        dataSource.getCharacters(name, offset, count)

    suspend fun getCharacterDetails(characterId: Int): Result<Characters?> =
        dataSource.getCharacterDetails(characterId)
}