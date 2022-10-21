package com.example.tmdb.domain.filmDetails

import com.example.tmdb.data.filmDetails.FilmDetailsDTO
import retrofit2.Response

interface FilmDetailsRepository {
    suspend fun getDetails(id: Int): Response<FilmDetailsDTO>
}