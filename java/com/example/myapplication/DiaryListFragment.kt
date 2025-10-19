package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.activityViewModels

class DiaryListFragment : Fragment() {
    private lateinit var photoListView: ListView
    private val photoDiaryViewModel: PhotoDiaryViewModel by activityViewModels()
    private lateinit var adapter: DiaryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_diary_list, container, false)
        photoListView = view.findViewById(R.id.diaryListView)

        // 一開始用空清單建立 adapter，注意要用 MutableList
        adapter = DiaryAdapter(requireContext(), mutableListOf())
        photoListView.adapter = adapter

        // 觀察 LiveData 更新 ListView
        photoDiaryViewModel.entries.observe(viewLifecycleOwner) { entries ->
            adapter.updateData(entries)
        }

        return view
    }
}
