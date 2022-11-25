package com.example.testjetpack

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LogViewModel : ViewModel() {
    val buzzLiveData: LiveData<List<String>>
        get() = _buzzLiveData

    private val _buzzLiveData = MutableLiveData<List<String>>(emptyList())

    fun addData(param: String) {
        _buzzLiveData.value = _buzzLiveData.value?.let { it + listOf(param) }
    }
}