package com.example.tmdb.data.filmList

import com.example.tmdb.domain.filmList.FilmListRepository

class FilmListRepositoryImpl(
    private val apiFilmListHelper: ApiFilmListHelper
) : FilmListRepository{
    override suspend fun getFilms() = apiFilmListHelper.getFilms()
}