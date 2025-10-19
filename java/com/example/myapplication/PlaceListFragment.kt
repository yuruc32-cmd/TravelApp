package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.activityViewModels

class PlaceListFragment : Fragment() {
    private lateinit var placeListView: ListView
    private val placeViewModel: PlaceViewModel by activityViewModels()
    private lateinit var adapter: PlaceAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_place_list, container, false)
        placeListView = view.findViewById(R.id.placeListView)

        // 一開始用空清單建立 adapter
        adapter = PlaceAdapter(requireContext(), mutableListOf())
        placeListView.adapter = adapter

        // 觀察 LiveData 更新 ListView
        placeViewModel.places.observe(viewLifecycleOwner) { places ->
            adapter.updateData(places)
        }

        return view
    }


    }

