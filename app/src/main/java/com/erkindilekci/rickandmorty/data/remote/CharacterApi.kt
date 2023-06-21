package com.erkindilekci.rickandmorty.data.remote

import com.erkindilekci.rickandmorty.data.remote.dto.Character
import com.erkindilekci.rickandmorty.data.remote.dto.detail.CharacterDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterApi {

    @GET("/api/character")
    suspend fun getCharacters(
        @Query("page") page: Int = 0
    ): Response<Character>

    @GET("api/character/{id}")
    suspend fun getCharacterInfo(
        @Path("id") id: Int
    ): CharacterDetail
}
