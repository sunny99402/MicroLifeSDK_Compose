package com.example.testjetpack

import androidx.compose.runtime.MutableState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LogViewModel : ViewModel() {
    var name: String? = ""
        get() = _name.value
    val buzzLiveData: LiveData<List<String>>
        get() = _buzzLiveData

    private var _name = MutableLiveData("")
    private val _buzzLiveData = MutableLiveData<List<String>>(emptyList())

    fun addData(param: String) {
        _buzzLiveData.value = _buzzLiveData.value?.let { it + listOf(param) }
    }

    fun deviceName(name :String) {
        this._name.value = name
    }

}