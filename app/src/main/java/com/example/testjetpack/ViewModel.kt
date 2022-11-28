package com.example.testjetpack

import androidx.compose.runtime.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewModel : ViewModel() {
    private val _buzzLiveData = MutableLiveData<List<String>>(emptyList())
    val buzzLiveData: LiveData<List<String>>
        get() = _buzzLiveData

    var name by mutableStateOf("")
    var isConnect by mutableStateOf(false)

    fun addData(param: String) {
        _buzzLiveData.value = _buzzLiveData.value?.let { it + listOf(param) }
    }

    fun deviceName(name: String) {
        this.name = name
    }

    fun setConnectState(b: Boolean) {
        this.isConnect = b
    }
}