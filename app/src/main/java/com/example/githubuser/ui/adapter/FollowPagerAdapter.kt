package com.example.githubuser.ui.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubuser.ui.fragment.FollowFrag

class FollowPagerAdapter(activity: AppCompatActivity, private val username: String) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment: Fragment = FollowFrag()
        fragment.arguments = Bundle().apply {
            putInt(FollowFrag.KEY_POSITION, position)
            putString(FollowFrag.KEY_USERNAME, username)
        }
        return fragment
    }
}