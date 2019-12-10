package com.medialink.sub2catalogue.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Bambang Ariyanto"
    }
    val text: LiveData<String> = _text
}