package com.example.tmdb.domain.filmDetails

import com.example.tmdb.BuildConfig
import com.example.tmdb.data.filmDetails.FilmDetailsDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface FilmDetailsApi {
    @GET("{id}?api_key=${BuildConfig.tmdb_api_key}")
    suspend fun getDetails(@Path("id") id: Int?): Response<FilmDetailsDTO>
}