package com.example.tmdb.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.tmdb.data.filmList.FilmListDTO
import com.example.tmdb.domain.filmList.FilmListRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel

class MainScreenViewModel(
    private val filmListRepository: FilmListRepository
) : ViewModel() {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    val errorMessage = MutableLiveData<String>()

    private var filmListJob: Job? = null

    fun getFilmList(): LiveData<PagingData<FilmListDTO.Result>> {
        return filmListRepository.getFilms().cachedIn(viewModelScope)
    }

    override fun onCleared() {
        coroutineScope.cancel()
        filmListJob?.cancel()
        super.onCleared()
    }
}