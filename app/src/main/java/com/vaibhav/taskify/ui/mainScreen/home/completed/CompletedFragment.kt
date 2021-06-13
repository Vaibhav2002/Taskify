package com.vaibhav.taskify.ui.mainScreen.home.completed

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.vaibhav.taskify.R
import com.vaibhav.taskify.databinding.FragmentCompletedBinding
import com.vaibhav.taskify.ui.adapters.TaskAdapter
import com.vaibhav.taskify.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import timber.log.Timber

@AndroidEntryPoint
class CompletedFragment : Fragment(R.layout.fragment_completed) {

    companion object {
        fun newInstance() = CompletedFragment()
    }

    private lateinit var completedTaskAdapter: TaskAdapter
    private val binding by viewBinding(FragmentCompletedBinding::bind)
    private val viewModel: CompletedViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        completedTaskAdapter = TaskAdapter {

        }
        binding.completedTaskRv.apply {
            adapter = completedTaskAdapter
            setHasFixedSize(false)
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.completedTasks.collect {
                Timber.d(it.toString())
                completedTaskAdapter.submitList(it)
            }
        }
    }

}