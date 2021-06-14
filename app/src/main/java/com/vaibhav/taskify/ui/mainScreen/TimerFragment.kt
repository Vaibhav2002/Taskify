package com.vaibhav.taskify.ui.mainScreen

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.vaibhav.taskify.R
import com.vaibhav.taskify.databinding.FragmentTimerBinding
import com.vaibhav.taskify.ui.stopwatchScreen.TimerViewModel
import com.vaibhav.taskify.util.StopWatchFor
import com.vaibhav.taskify.util.setBackground
import com.vaibhav.taskify.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TimerFragment : Fragment(R.layout.fragment_timer) {


    private val binding by viewBinding(FragmentTimerBinding::bind)
    private val viewModel: TimerViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val task = mainViewModel.task
        viewModel.task = task
        val stopWatchFor = mainViewModel.stopWatchFor
        binding.apply {
            if (task != null && stopWatchFor != null) {
                taskTitle.text = task.task_title
                taskDescription.text = task.task_description
                binding.taskTagTv.setBackground(task.task_category)
                binding.taskTagTv.text = task.task_category.name
                when (stopWatchFor) {
                    StopWatchFor.RUNNING -> {
                        binding.startPauseButton.text = "PAUSE"
                        binding.startPauseButton.isVisible = true
                        binding.stopButton.isVisible = true
                    }
                    StopWatchFor.PAUSED -> {
                        binding.startPauseButton.text = "START"
                        binding.startPauseButton.isVisible = true
                        binding.stopButton.isVisible = true
                    }
                    StopWatchFor.UPCOMING -> {
                        binding.startPauseButton.text = "START"
                        binding.startPauseButton.isVisible = true
                        binding.stopButton.isVisible = false
                    }
                }
            }
        }
        binding.backArrow.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.startPauseButton.setOnClickListener {
            if (stopWatchFor == StopWatchFor.RUNNING)
                viewModel.pauseTask(mainViewModel.timeLeft.value)
            else
                viewModel.startTask()
        }
        binding.stopButton.setOnClickListener {
            viewModel.stopTask(mainViewModel.timeLeft.value)
        }
//        lifecycleScope.launchWhenStarted {
//            viewModel.startTaskState.collect {
//                when (it) {
//                    is Resource.Empty -> Unit
//                    is Resource.Error -> {
//                        requireContext().showToast(it.message)
//                    }
//                    is Resource.Loading -> {
//
//                    }
//                    is Resource.Success -> {
//
//                    }
//                }
//            }
//        }
    }
}