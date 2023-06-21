package com.erkindilekci.rickandmorty.domain.repository

import androidx.paging.PagingData
import com.erkindilekci.rickandmorty.data.remote.dto.Result
import com.erkindilekci.rickandmorty.data.remote.dto.detail.CharacterDetail
import com.erkindilekci.rickandmorty.util.Resource
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

    fun getPaginatedCharacters(): Flow<PagingData<Result>>

    suspend fun getCharacterDetail(id: Int): Resource<CharacterDetail>
}
