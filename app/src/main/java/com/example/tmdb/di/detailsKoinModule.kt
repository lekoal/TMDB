package com.example.tmdb.di

import com.example.tmdb.data.filmDetails.ApiFilmDetailsHelper
import com.example.tmdb.data.filmDetails.FilmDetailsRepositoryImpl
import com.example.tmdb.domain.filmDetails.FilmDetailsApi
import com.example.tmdb.domain.filmDetails.FilmDetailsRepository
import com.example.tmdb.ui.details.DetailsFragment
import com.example.tmdb.ui.details.DetailsViewModel
import com.example.tmdb.utils.ApiUtils
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val detailsKoinModule = module {

    single(named("film_details_base_url")) {
        ApiUtils.baseUrlFilmDetails
    }

    single(named("image_base_url")) {
        ApiUtils.imageBaseUrl
    }

    single(named("film_details_api")) {
        get<Retrofit>(named("film_details_retrofit")).create(FilmDetailsApi::class.java)
    }

    single(named("api_film_details_helper")) {
        ApiFilmDetailsHelper(get(named("film_details_api")))
    }

    single<FilmDetailsRepository>(named("film_details_repository")) {
        FilmDetailsRepositoryImpl(get(named("api_film_details_helper")))
    }

    single<Retrofit>(named("film_details_retrofit")) {
        Retrofit.Builder()
            .baseUrl(get<String>(named("film_details_base_url")))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    scope<DetailsFragment> {
        viewModel(named("details_view_model")) {
            DetailsViewModel(
                get(named("film_details_repository"))
            )
        }
    }
}