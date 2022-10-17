package com.example.tmdb.data.filmDetails

import com.example.tmdb.domain.filmDetails.FilmDetailsRepository

class FilmDetailsRepositoryImpl(
    private val apiFilmDetailsHelper: ApiFilmDetailsHelper
) : FilmDetailsRepository {
    override suspend fun getDetails(id: Int) = apiFilmDetailsHelper.getDetails(id)
}