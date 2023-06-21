package com.erkindilekci.rickandmorty.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.erkindilekci.rickandmorty.data.remote.CharacterApi
import com.erkindilekci.rickandmorty.data.remote.dto.Result
import java.io.IOException
import javax.inject.Inject

class CharacterPagingSource @Inject constructor(
    private val api: CharacterApi
) : PagingSource<Int, Result>() {

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {
            val page = params.key ?: 0
            val response = api.getCharacters(page = page)

            if (response.isSuccessful) {
                val body = response.body()?.results ?: emptyList()
                LoadResult.Page(
                    data = body,
                    prevKey = if (page == 0) null else page - 1,
                    nextKey = if (body.isEmpty()) null else page + 1
                )
            } else {
                LoadResult.Error(IOException())
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
