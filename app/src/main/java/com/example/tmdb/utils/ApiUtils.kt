package com.example.tmdb.utils

object ApiUtils {
    private const val baseUrlMainPart = "https://api.themoviedb.org/"
    private const val baseUrlVersion = "3/"
    private const val discoverPart = "discover/"
    private const val detailsPart = "movie/"
    const val baseUrlFilmList = "$baseUrlMainPart$baseUrlVersion$discoverPart"
    const val baseUrlFilmDetails = "$baseUrlMainPart$baseUrlVersion$detailsPart"
}