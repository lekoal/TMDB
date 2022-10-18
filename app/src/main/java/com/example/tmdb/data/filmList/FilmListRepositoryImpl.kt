package com.example.tmdb.data.filmList

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.tmdb.domain.filmList.FilmListApi
import com.example.tmdb.domain.filmList.FilmListRepository

private const val NETWORK_PAGE_SIZE = 20

class FilmListRepositoryImpl(
    private val filmListApi: FilmListApi
) : FilmListRepository{
    override fun getFilms(): LiveData<PagingData<FilmListDTO.Result>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = 3
            ),
            pagingSourceFactory = {
                FilmListPagingSource(filmListApi)
            },
            initialKey = 1
        ).liveData
    }
}