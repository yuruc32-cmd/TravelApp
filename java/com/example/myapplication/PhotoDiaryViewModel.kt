package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.PhotoDiaryEntry

class PhotoDiaryViewModel : ViewModel() {
    private val _entries = MutableLiveData<MutableList<PhotoDiaryEntry>>(mutableListOf())
    val entries: MutableLiveData<MutableList<PhotoDiaryEntry>> = _entries

    fun addEntry(entry: PhotoDiaryEntry) {
        _entries.value?.add(entry)
        _entries.value = _entries.value // 通知 LiveData 更新
    }
}
