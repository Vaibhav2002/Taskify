package com.vaibhav.taskify.ui.mainScreen.stats

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.vaibhav.taskify.R
import com.vaibhav.taskify.databinding.FragmentStatsBinding
import com.vaibhav.taskify.ui.adapters.TaskAdapter
import com.vaibhav.taskify.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class StatsFragment : Fragment(R.layout.fragment_stats) {

    private val binding by viewBinding(FragmentStatsBinding::bind)
    private val viewModel: StatsViewModel by viewModels()
    private lateinit var taskAdapter: TaskAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        taskAdapter = TaskAdapter {

        }
        binding.allTaskRecycler.apply {
            adapter = taskAdapter
            setHasFixedSize(false)
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.dataFormatted.collect {
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.lastWeekTasks.collect {
                taskAdapter.submitList(it)
            }
        }

    }


}