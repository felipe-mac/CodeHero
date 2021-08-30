package com.app.codehero.data

import com.app.codehero.api.Result
import com.app.codehero.domain.model.Character
import com.app.codehero.domain.model.Characters

interface CharacterDataSource {
    suspend fun getChars(name: String, offset: Int, count: Int): Result<Characters?>

    suspend fun getCharacterDetails(characterId: Int): Result<Characters?>
}