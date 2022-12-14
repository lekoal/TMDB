package com.example.tmdb.domain.filmList

import com.example.tmdb.BuildConfig
import com.example.tmdb.data.filmList.FilmListDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FilmListApi {
    @GET("movie?api_key=${BuildConfig.tmdb_api_key}")
    suspend fun getFilms(
        @Query("page") page: Int
    ): Response<FilmListDTO>
}