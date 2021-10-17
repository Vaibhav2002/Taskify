package com.vaibhav.taskify.ui.mainScreen.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.vaibhav.taskify.R
import com.vaibhav.taskify.databinding.FragmentHomeBinding
import com.vaibhav.taskify.ui.mainScreen.MainViewModel
import com.vaibhav.taskify.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)
    private lateinit var homePagerAdapter: HomePagerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homePagerAdapter = HomePagerAdapter(requireActivity())
        initViewPager()
        Timber.d("In home fragment")
        binding.addTask.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addTaskActivity)
        }
    }

    private fun initViewPager() {
        binding.viewpager.adapter = homePagerAdapter
        TabLayoutMediator(binding.tableLayout, binding.viewpager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Ongoing"
                }
                1 -> {
                    tab.text = "Upcoming"
                }
                2 -> {
                    tab.text = "Completed"
                }
            }
        }.attach()
    }
}
