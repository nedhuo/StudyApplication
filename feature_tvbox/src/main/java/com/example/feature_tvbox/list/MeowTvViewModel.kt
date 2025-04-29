package com.example.feature_tvbox.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lib_spider.meowtv.spider.MeowTvSpider
import kotlinx.coroutines.launch

class MeowTvViewModel : ViewModel() {
    private val _data = MutableLiveData<String>()
    val data: LiveData<String> = _data

    fun loadData(url: String) {
        viewModelScope.launch {
            try {
                _data.value = MeowTvSpider.fetchMeowTvSitesJson(url)
                Log.d("MeowTvViewModel",  "Data: ${_data.value}")
            } catch (e: Exception) {
                _data.value = "Error: ${e.message}"
            }
        }
    }
}