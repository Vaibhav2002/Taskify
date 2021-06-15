package com.vaibhav.taskify.ui.mainScreen.timer

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.vaibhav.taskify.R
import com.vaibhav.taskify.databinding.FragmentTimerBinding
import com.vaibhav.taskify.service.ServiceTimer
import com.vaibhav.taskify.service.TimerState
import com.vaibhav.taskify.ui.mainScreen.MainViewModel
import com.vaibhav.taskify.util.StopWatchFor
import com.vaibhav.taskify.util.TaskState
import com.vaibhav.taskify.util.setBackground
import com.vaibhav.taskify.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import timber.log.Timber

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
                        setViewsForRunningTask()
                    }
                    StopWatchFor.PAUSED -> {
                        setViewsForPausedTask()
                    }
                    StopWatchFor.UPCOMING -> {
                        setViewsForNotStartedTask()
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
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.startTaskState.collect {

                Timber.d(it.toString())
            }
        }
        binding.stopButton.setOnClickListener {
            viewModel.stopTask(mainViewModel.timeLeft.value)
        }

        ServiceTimer.timerState.observe(viewLifecycleOwner) {
            if (it == TimerState.STOP) {
                findNavController().popBackStack()
                ServiceTimer.timerState.postValue(null)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.operation.collect {
                when (it) {
                    TaskState.PAUSED -> setViewsForPausedTask()
                    TaskState.RUNNING -> setViewsForRunningTask()
                    TaskState.COMPLETED -> findNavController().popBackStack()
                }
            }
        }
    }

    private fun setViewsForNotStartedTask() {
        binding.startPauseButton.text = "START"
        binding.startPauseButton.isVisible = true
        binding.stopButton.isVisible = false
    }

    private fun setViewsForPausedTask() {
        binding.startPauseButton.text = "START"
        binding.startPauseButton.isVisible = true
        binding.stopButton.isVisible = true
    }

    private fun setViewsForRunningTask() {
        binding.startPauseButton.text = "PAUSE"
        binding.startPauseButton.isVisible = true
        binding.stopButton.isVisible = true
        setUpProgressBar()
    }


    private fun setUpProgressBar() {

    }

}