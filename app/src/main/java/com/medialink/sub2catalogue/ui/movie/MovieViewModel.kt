package com.medialink.sub2catalogue.ui.movie

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.medialink.sub2catalogue.OperationCallback
import com.medialink.sub2catalogue.data.movie.MovieDataSource
import com.medialink.sub2catalogue.data.movie.MovieRepository
import com.medialink.sub2catalogue.models.movie.Movie
import java.util.*

class MovieViewModel(private val repository: MovieDataSource) : ViewModel() {

    constructor() : this(MovieRepository())

    private val _movies = MutableLiveData<List<Movie>>().apply { value = emptyList() }
    val movies: LiveData<List<Movie>> = _movies

    private val _isViewLoading = MutableLiveData<Boolean>().apply { value = false }
    val isViewLoading: LiveData<Boolean> = _isViewLoading

    private val _isEmptyList = MutableLiveData<Boolean>().apply { value = false }
    val isEmptyList: LiveData<Boolean> = _isEmptyList

    private val _onMessageError = MutableLiveData<Any>()
    val onMessageError: LiveData<Any> = _onMessageError

    var lokal: String = Locale.getDefault().language

    /*
    If you require that the data be loaded only once, you can consider calling the method
    "loadMovies()" on constructor. Also, if you rotate the screen, the service will not be called.
    */
    init {
        loadMovie(repository.page)
    }

    fun loadMovie(page: Int) {
        _isViewLoading.value = true

        repository.page = page

        val iso3Country = Locale.getDefault().country
        if (Locale.getDefault().language.equals("in", true)) {
            repository.language = "id-${iso3Country}"
        } else {
            val language = Locale.getDefault().language
            repository.language = "${language}-${iso3Country}"
        }

        Log.d("debug", Locale.getDefault().language)       //---> en
        Log.d("debug", Locale.getDefault().isO3Language)  //---> eng
        Log.d("debug", Locale.getDefault().country)    //---> US
        Log.d("debug", Locale.getDefault().isO3Country)   //---> USA
        Log.d("debug", Locale.getDefault().displayCountry)//---> United States
        Log.d("debug", Locale.getDefault().displayName)   //---> English (United States)
        Log.d("debug", Locale.getDefault().toString())       //---> en_US
        Log.d("debug", Locale.getDefault().displayLanguage)//---> English

        repository.retriveMovie(object : OperationCallback {
            override fun onSuccess(obj: Any?) {
                _isViewLoading.value = false
                if (obj != null && obj is List<*>) {
                    if (obj.isEmpty()) {
                        _isEmptyList.value = true
                    } else {
                        _movies.value = obj as List<Movie>
                    }
                }
            }

            override fun onError(obj: Any?) {
                _isViewLoading.value = false
                _onMessageError.value = obj
            }
        })
    }

}