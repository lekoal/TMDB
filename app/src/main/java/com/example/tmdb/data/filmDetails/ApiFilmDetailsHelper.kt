package com.example.tmdb.data.filmDetails

import com.example.tmdb.domain.filmDetails.FilmDetailsApi

class ApiFilmDetailsHelper(private val filmDetailsApi: FilmDetailsApi) {
    suspend fun getDetails(id: Int) = filmDetailsApi.getDetails(id)
}