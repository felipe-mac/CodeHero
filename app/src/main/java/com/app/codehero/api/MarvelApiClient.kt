package com.app.codehero.api
import com.app.codehero.domain.model.Character
import com.app.codehero.domain.model.Characters
import com.app.codehero.domain.model.ResponseDefault
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

/**
FELIPE
 */
interface MarvelApiClient {

    @GET("characters")
    suspend fun getCharacters(
        @QueryMap(encoded = true) params: Map<String, String>
    ): Response<ResponseDefault<Characters>>
    /*        @Query("name") name: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
        @Query("ts") timeStamp: String = "123456",
        @Query("apikey") apiKey: String = "2efa65bb32d8274ddef139a280e79fd9",
        @Query("hash") hash: String = "bd68d0d5cda9d525fa2d1fe97a4c7088"*/

    @GET("characters/{characterId}")
    suspend fun getCharacterDetails(
        @Path("characterId") characterId: Int,
        @QueryMap(encoded = true) params: Map<String, String>
    ): Response<ResponseDefault<Characters>>

}