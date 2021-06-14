package com.example.authboy.ui.fragments.About

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AboutViewModel: ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Version 1.0.0.Alphat01"
    }
    val text: LiveData<String> = _text
}