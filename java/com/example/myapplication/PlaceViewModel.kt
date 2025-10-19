package com.example.myapplication

import Place
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PlaceViewModel : ViewModel() {
    // 私有可變清單
    private val _places = MutableLiveData<MutableList<Place>>(mutableListOf(
        Place("台北101", "臺北市信義區信義路5段7號", "台灣著名地標",

            webUrl = "https://zh.wikipedia.org/wiki/%E5%8F%B0%E5%8C%97101"),
        Place("士林夜市", "111台北市士林區基河路101號士林夜市", "熱門夜市",

            webUrl = "https://zh.wikipedia.org/wiki/%E5%A3%AB%E6%9E%97%E5%A4%9C%E5%B8%82")
    ))

    // 外部只能觀察，不能修改
    val places: LiveData<MutableList<Place>> = _places

    // 新增景點
    fun addPlace(place: Place) {
        _places.value?.add(place)
        // 通知 LiveData 資料變更（一定要重新設定）
        _places.value = _places.value
    }
}
