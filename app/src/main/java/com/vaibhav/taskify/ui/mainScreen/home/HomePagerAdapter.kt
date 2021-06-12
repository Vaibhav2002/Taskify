package com.vaibhav.taskify.ui.mainScreen.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.vaibhav.taskify.ui.mainScreen.home.completed.CompletedFragment
import com.vaibhav.taskify.ui.mainScreen.home.onGoing.OnGoingFragment
import com.vaibhav.taskify.ui.mainScreen.home.upComing.UpComingFragment

class HomePagerAdapter(
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> OnGoingFragment.newInstance()
            1 -> UpComingFragment.newInstance()
            else -> CompletedFragment.newInstance()
        }
    }
}