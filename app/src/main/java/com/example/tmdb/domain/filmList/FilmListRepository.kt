package com.example.tmdb.domain.filmList

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.tmdb.data.filmList.FilmListDTO

interface FilmListRepository {
    fun getFilms(): LiveData<PagingData<FilmListDTO.Result>>
}