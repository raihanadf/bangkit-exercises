package com.example.mytablayout.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mytablayout.HomeFragment

class SectionsPagerAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity) {

    var appName: String = ""

    override fun createFragment(position: Int): Fragment {
        val fragment = HomeFragment()
        fragment.arguments = Bundle().apply {
            putInt(HomeFragment.ARG_SECTION_NUMBER, position + 1)
            putString(HomeFragment.ARG_NAME, appName)
        }
        return fragment
    }

    override fun getItemCount(): Int {
        return 3
    }
}