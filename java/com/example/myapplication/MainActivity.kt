package com.example.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.ListFragment
import androidx.viewpager2.widget.ViewPager2

import com.example.myapplication.PlaceListFragment

import com.example.myapplication.PhotoDiaryFragment

import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.fragments.AddPlaceFragment


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        // 初始化 ViewPager2 與 Adapter
        val viewPager2 = findViewById<ViewPager2>(R.id.viewPager2)

        val adapter = ViewPagerAdapter(supportFragmentManager, this.lifecycle)
        viewPager2.adapter = adapter
        viewPager2.offscreenPageLimit = 1//預先載入左右畫面

        // BottomNavigation 切換時改變 ViewPager2 頁面
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_list -> binding.viewPager2.currentItem = 0
                R.id.nav_add -> binding.viewPager2.currentItem = 1
                R.id.nav_diary -> binding.viewPager2.currentItem = 2
                R.id.nav_photolist -> binding.viewPager2.currentItem = 3
            }
            true
        }

        // ViewPager2 滑動時同步 BottomNavigation
        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                val itemId = when (position) {
                    0 -> R.id.nav_list
                    1 -> R.id.nav_add
                    2 -> R.id.nav_diary
                    3 -> R.id.nav_photolist
                    else -> R.id.nav_list
                }
                binding.bottomNavigation.selectedItemId = itemId
            }
        })
    }
}