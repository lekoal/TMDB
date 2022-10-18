package com.example.tmdb.di

import com.example.tmdb.data.filmDetails.ApiFilmDetailsHelper
import com.example.tmdb.data.filmDetails.FilmDetailsRepositoryImpl
import com.example.tmdb.data.filmList.FilmListRepositoryImpl
import com.example.tmdb.domain.filmDetails.FilmDetailsApi
import com.example.tmdb.domain.filmDetails.FilmDetailsRepository
import com.example.tmdb.domain.filmList.FilmListApi
import com.example.tmdb.domain.filmList.FilmListRepository
import com.example.tmdb.ui.main.MainScreenFragment
import com.example.tmdb.ui.main.MainScreenPagerAdapter
import com.example.tmdb.ui.main.MainScreenViewModel
import com.example.tmdb.utils.ApiUtils
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val mainKoinModule = module {
    single(named("film_list_base_url")) {
        ApiUtils.baseUrlFilmList
    }
    single(named("film_details_base_url")) {
        ApiUtils.baseUrlFilmDetails
    }

    single(named("film_list_api")) {
        get<Retrofit>(named("film_list_retrofit")).create(FilmListApi::class.java)
    }

    single(named("film_details_api")) {
        get<Retrofit>(named("film_details_retrofit")).create(FilmDetailsApi::class.java)
    }

    single(named("api_film_details_helper")) {
        ApiFilmDetailsHelper(get(named("film_details_api")))
    }

    single<Retrofit>(named("film_list_retrofit")) {
        Retrofit.Builder()
            .baseUrl(get<String>(named("film_list_base_url")))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<Retrofit>(named("film_details_retrofit")) {
        Retrofit.Builder()
            .baseUrl(get<String>(named("film_details_base_url")))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<FilmListRepository>(named("film_list_repository")) {
        FilmListRepositoryImpl(get(named("film_list_api")))
    }

    single<FilmDetailsRepository>(named("film_details_repository")) {
        FilmDetailsRepositoryImpl(get(named("api_film_details_helper")))
    }

    scope<MainScreenFragment> {
        viewModel(named("main_screen_view_model")) {
            MainScreenViewModel(
                get(named("film_list_repository")),
                get(named("film_details_repository"))
            )
        }
        scoped(named("main_screen_adapter")) {
            MainScreenPagerAdapter()
        }
    }

}