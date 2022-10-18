package com.example.tmdb.data.filmList

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.tmdb.domain.filmList.FilmListApi

class FilmListPagingSource(private val filmListApi: FilmListApi) :
    PagingSource<Int, FilmListDTO.Result>() {
    override fun getRefreshKey(state: PagingState<Int, FilmListDTO.Result>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1) ?:
            state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FilmListDTO.Result> {
        return try {
            val position = params.key ?: 1
            val response = filmListApi.getFilms(position)
            LoadResult.Page(
                data = response.body()!!.results,
                prevKey = if (position == 1) null
                else position - 1,
                nextKey = position + 1)
        } catch (e: java.lang.Exception) {
            LoadResult.Error(e)
        }
    }

}