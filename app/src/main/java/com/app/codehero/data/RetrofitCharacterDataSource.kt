package com.app.codehero.data

import android.util.Log
import com.app.codehero.api.Result
import com.app.codehero.api.WebApiAccess
import com.app.codehero.domain.model.Characters
import com.app.codehero.utils.addCredentialsMarvelApi

class RetrofitCharacterDataSource: CharacterDataSource {

    override suspend fun getCharacters(name: String, offset: Int, count: Int) : Result<Characters?> {
        return try {
            val map = mutableMapOf<String, Any>(
                "offset" to offset,
                "limit" to count
            )
            if(name.isNotBlank()) map["nameStartsWith"] = name
            addCredentialsMarvelApi(map)

            val result = WebApiAccess.marvelApi.getCharacters(map as Map<String, String>)
            if (result.isSuccessful) {
                Result.Success(result.body()?.data)
            } else {
                Result.Failure(result.code(), Exception(result.message()))
            }
        } catch (e:Exception) {
            e.printStackTrace()
            Result.Error(e)
        }
    }

    override suspend fun getCharacterDetails(characterId: Int): Result<Characters?> {
        return try {
            val map = mutableMapOf<String, Any>()
            addCredentialsMarvelApi(map)
            val result = WebApiAccess.marvelApi.getCharacterDetails(characterId = characterId, map as Map<String, String>)
            if (result.isSuccessful) {
                Result.Success(result.body()?.data)
            } else {
                Result.Failure(result.code(), Exception(result.message()))
            }
        } catch (e:Exception) {
            e.printStackTrace()
            Result.Error(e)
        }
    }
}