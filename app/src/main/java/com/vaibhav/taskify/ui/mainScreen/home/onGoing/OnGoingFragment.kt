package com.vaibhav.taskify.ui.mainScreen.home.onGoing

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.vaibhav.taskify.R
import com.vaibhav.taskify.databinding.FragmentOnGoingBinding
import com.vaibhav.taskify.ui.adapters.OnGoingTaskAdapter
import com.vaibhav.taskify.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import timber.log.Timber

@AndroidEntryPoint
class OnGoingFragment : Fragment(R.layout.fragment_on_going) {

    companion object {
        fun newInstance() = OnGoingFragment()
    }

    private val binding by viewBinding(FragmentOnGoingBinding::bind)
    private val viewModel: OnGoingViewModel by viewModels()
    private lateinit var onGoingAdapter: OnGoingTaskAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onGoingAdapter = OnGoingTaskAdapter {

        }
        binding.runningTaskRv.apply {
            adapter = onGoingAdapter
            setHasFixedSize(false)
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.onGoingTasks.collect {
                Timber.d(it.toString())
                onGoingAdapter.submitList(it)
            }
        }

    }

}