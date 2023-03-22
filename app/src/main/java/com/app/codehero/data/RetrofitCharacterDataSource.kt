package com.app.codehero.data

import com.app.codehero.api.Result
import com.app.codehero.api.WebApiAccess
import com.app.codehero.domain.model.Character
import com.app.codehero.domain.model.Characters
import javax.inject.Inject

class RetrofitCharacterDataSource @Inject constructor(): CharacterDataSource {

    override suspend fun getChars(name: String, offset: Int, count: Int) : Result<Characters?> {
        return try {
            val map = mutableMapOf(
                "offset" to offset,
                "limit" to count,
                "ts" to "123456",
                "apikey" to "2efa65bb32d8274ddef139a280e79fd9",
                "hash" to "bd68d0d5cda9d525fa2d1fe97a4c7088"
            )
            if(name.isNotBlank()) map["nameStartsWith"] = name

            val result = WebApiAccess.marvelApi.getCharacters(map as Map<String, String>)
            if (result.isSuccessful) {
                Result.Success(result.body()?.data)
            } else {
                Result.Failure(result.code(), Exception(result.message()))
            }
        } catch (e:Exception) {
            Result.Failure(500, e)
        }
    }

    override suspend fun getCharacterDetails(characterId: Int): Result<Characters?> {
        return try {
            val result = WebApiAccess.marvelApi.getCharacterDetails(characterId = characterId)
            if (result.isSuccessful) {
                Result.Success(result.body()?.data)
            } else {
                Result.Failure(result.code(), Exception(result.message()))
            }
        } catch (e:Exception) {
            e.printStackTrace()
            Result.Failure(500, e)
        }
    }
}