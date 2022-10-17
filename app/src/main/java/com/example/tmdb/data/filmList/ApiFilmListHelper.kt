package com.example.tmdb.data.filmList

import com.example.tmdb.domain.filmList.FilmListApi

class ApiFilmListHelper(private val filmListApi: FilmListApi) {
    suspend fun getFilms() = filmListApi.getFilms()
}