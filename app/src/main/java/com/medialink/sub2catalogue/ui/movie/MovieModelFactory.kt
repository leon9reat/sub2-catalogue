package com.medialink.sub2catalogue.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.medialink.sub2catalogue.data.movie.MovieDataSource

class MovieModelFactory(private val repository: MovieDataSource) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieViewModel(repository) as T
    }
}