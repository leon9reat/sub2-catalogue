package com.medialink.sub2catalogue.ui.tv_show

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.medialink.sub2catalogue.OperationCallback
import com.medialink.sub2catalogue.data.tv_show.TvShowRepository
import com.medialink.sub2catalogue.models.tv_show.TvShow
import java.util.*

class TvShowViewModel(private val repository: TvShowRepository) : ViewModel() {

    constructor() : this(TvShowRepository())

    private val _tvShows = MutableLiveData<List<TvShow>>().apply {
        value = emptyList()
    }
    val tvShows: LiveData<List<TvShow>> = _tvShows

    private val _isViewLoading = MutableLiveData<Boolean>()
    val isViewLoading: LiveData<Boolean> = _isViewLoading

    private val _isEmptyList = MutableLiveData<Boolean>()
    val isEmptyList: LiveData<Boolean> = _isEmptyList

    private val _onMessageError = MutableLiveData<Any>()
    val onMessageError: LiveData<Any> = _onMessageError

    var lokal = Locale.getDefault().language

    init {
        loadTvShow(repository.page)
    }

    fun loadTvShow(aPage: Int) {
        _isViewLoading.value = true

        repository.page = aPage
        val iso3Country = Locale.getDefault().country
        if (Locale.getDefault().language.equals("in", true)) {
            repository.language = "id-${iso3Country}"
        } else {
            val language = Locale.getDefault().language
            repository.language = "${language}-${iso3Country}"
        }

        repository.retriveTvShow(object : OperationCallback {
            override fun onSuccess(obj: Any?) {
                _isViewLoading.value = false
                if (obj != null && obj is List<*>) {
                    if (obj.isEmpty()) {
                        _isEmptyList.value = true
                    } else {
                        _tvShows.value = obj as List<TvShow>
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