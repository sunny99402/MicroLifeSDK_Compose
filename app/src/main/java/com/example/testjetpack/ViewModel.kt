package com.example.testjetpack

import androidx.compose.runtime.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewModel : ViewModel() {
    private val _listData = MutableLiveData<List<String>>(emptyList())
    val listData: LiveData<List<String>>
        get() = _listData

    var name by mutableStateOf("")
    var isConnect by mutableStateOf(false)

    fun addData(param: String) {
        _listData.value = _listData.value?.let { it + listOf(param) }
    }

    fun deviceName(name: String) {
        this.name = name
    }

    fun setConnectState(b: Boolean) {
        this.isConnect = b
    }
}