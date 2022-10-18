package com.example.tmdb.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.tmdb.data.filmDetails.FilmDetailsDTO
import com.example.tmdb.data.filmList.FilmListDTO
import com.example.tmdb.domain.filmDetails.FilmDetailsRepository
import com.example.tmdb.domain.filmList.FilmListRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel

class MainScreenViewModel(
    private val filmListRepository: FilmListRepository,
    private val filmDetailsRepository: FilmDetailsRepository
) : ViewModel() {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading
    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error
    val errorMessage = MutableLiveData<String>()

    private val _filmDetails = MutableLiveData<FilmDetailsDTO>()
    val filmDetails: LiveData<FilmDetailsDTO> = _filmDetails

    private var filmListJob: Job? = null
    private var filmDetailsJob: Job? = null

    fun getFilmList(): LiveData<PagingData<FilmListDTO.Result>> {
        return filmListRepository.getFilms().cachedIn(viewModelScope)
    }

//    fun getDetails(id: Int) {
//        filmDetailsJob = coroutineScope.launch {
//            _loading.postValue(true)
//            val response = filmDetailsRepository.getDetails(id)
//            withContext(Dispatchers.Main) {
//                if (response.isSuccessful) {
//                    _filmDetails.postValue(response.body())
//                    _loading.postValue(false)
//                } else {
//                    _error.postValue(true)
//                    _errorMessage.postValue(response.message())
//                    _loading.postValue(false)
//                }
//            }
//        }
//    }

    override fun onCleared() {
        coroutineScope.cancel()
        filmListJob?.cancel()
        filmDetailsJob?.cancel()
        super.onCleared()
    }
}