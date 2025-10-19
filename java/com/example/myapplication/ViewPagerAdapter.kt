package com.example.myapplication

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.lifecycle.Lifecycle
import androidx.fragment.app.FragmentManager
import com.example.myapplication.fragments.AddPlaceFragment

class ViewPagerAdapter (
    fm:FragmentManager,lifecycle: Lifecycle
) :FragmentStateAdapter(fm,lifecycle){

    override fun getItemCount(): Int =4

    override fun createFragment(position: Int): Fragment {
        return when (position){
            0 -> PlaceListFragment()
            1 -> AddPlaceFragment()
            2->PhotoDiaryFragment()
            else -> DiaryListFragment()
        }
    }




}