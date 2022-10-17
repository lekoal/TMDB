package com.example.tmdb.domain.filmList

import com.example.tmdb.data.filmList.FilmListDTO
import retrofit2.Response

interface FilmListRepository {
    suspend fun getFilms(): Response<FilmListDTO>
}