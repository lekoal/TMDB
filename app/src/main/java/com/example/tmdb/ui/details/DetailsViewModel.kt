package com.example.tmdb.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tmdb.data.filmDetails.FilmDetailsDTO
import com.example.tmdb.domain.filmDetails.FilmDetailsRepository
import kotlinx.coroutines.*

class DetailsViewModel(private val filmDetailsRepository: FilmDetailsRepository) : ViewModel() {
    private val _filmDetails = MutableLiveData<FilmDetailsDTO>()
    val filmDetails: LiveData<FilmDetailsDTO> = _filmDetails

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private var filmDetailsJob: Job? = null

    fun getDetails(id: Int) {
        filmDetailsJob = coroutineScope.launch {
            _loading.postValue(true)
            val response = filmDetailsRepository.getDetails(id)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    _filmDetails.postValue(response.body())
                    _loading.postValue(false)
                } else {
                    _error.postValue(true)
                    _errorMessage.postValue(response.message())
                    _loading.postValue(false)
                }
            }
        }
    }

    override fun onCleared() {
        coroutineScope.cancel()
        filmDetailsJob?.cancel()
        super.onCleared()
    }
}