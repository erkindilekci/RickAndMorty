package com.erkindilekci.rickandmorty.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.erkindilekci.rickandmorty.data.paging.CharacterPagingSource
import com.erkindilekci.rickandmorty.data.remote.CharacterApi
import com.erkindilekci.rickandmorty.data.remote.dto.Result
import com.erkindilekci.rickandmorty.data.remote.dto.detail.CharacterDetail
import com.erkindilekci.rickandmorty.domain.repository.CharacterRepository
import com.erkindilekci.rickandmorty.util.Resource
import kotlinx.coroutines.flow.Flow

class CharacterRepositoryImpl(
    private val api: CharacterApi
) : CharacterRepository {

    override fun getPaginatedCharacters(): Flow<PagingData<Result>> {
        return Pager(
            config = PagingConfig(pageSize = 20, initialLoadSize = 40, prefetchDistance = 10),
            pagingSourceFactory = { CharacterPagingSource(api) }
        ).flow
    }

    override suspend fun getCharacterDetail(id: Int): Resource<CharacterDetail> {
        val response = try {
            api.getCharacterInfo(id)
        } catch (e: Exception) {
            return Resource.Error(message = "An unknown error occurred.")
        }
        return Resource.Success(response)
    }
}
