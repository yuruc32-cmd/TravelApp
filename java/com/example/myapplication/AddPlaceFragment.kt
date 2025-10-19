package com.example.myapplication.fragments


import Place
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

import com.example.myapplication.PlaceViewModel
import com.example.myapplication.databinding.FragmentAddPlaceBinding
import com.example.myapplication.fragments.AddPlaceFragment



class AddPlaceFragment : Fragment() {

    private var _binding: FragmentAddPlaceBinding? = null
    private val binding get() = _binding!!

    // 使用 activityViewModels 取得共用 ViewModel
    private val viewModel: PlaceViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddPlaceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.buttonAddPlace.setOnClickListener {
            val name = binding.editTextName.text.toString().trim()
            val address = binding.editTextAddress.text.toString().trim()
            val description = binding.editTextDescription.text.toString().trim()
            val webUrl = binding.editTextWebUrl.text.toString().trim()

            if (name.isEmpty() || address.isEmpty() || description.isEmpty() || webUrl.isEmpty()) {
                Toast.makeText(requireContext(), "請填寫所有欄位", Toast.LENGTH_SHORT).show()
            } else {
                val newPlace = Place(name, address, description, webUrl)  // mapQuery = address
                viewModel.addPlace(newPlace)

                Toast.makeText(requireContext(), "新增成功！", Toast.LENGTH_SHORT).show()

                // 清空
                binding.editTextName.text.clear()
                binding.editTextAddress.text.clear()
                binding.editTextDescription.text.clear()
                binding.editTextWebUrl.text.clear()
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
