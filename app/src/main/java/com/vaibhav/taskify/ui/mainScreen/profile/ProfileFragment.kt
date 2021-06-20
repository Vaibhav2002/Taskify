package com.vaibhav.taskify.ui.mainScreen.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.vaibhav.taskify.R
import com.vaibhav.taskify.databinding.FragmentProfileBinding
import com.vaibhav.taskify.ui.adapters.TaskCountAdapter
import com.vaibhav.taskify.ui.auth.AuthActivity
import com.vaibhav.taskify.ui.mainScreen.profile.ProfileViewModel.ProfileScreenTaskCount.*
import com.vaibhav.taskify.util.setProfileImage
import com.vaibhav.taskify.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val binding by viewBinding(FragmentProfileBinding::bind)
    private val viewModel: ProfileViewModel by viewModels()
    private lateinit var taskCountAdapter: TaskCountAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        taskCountAdapter = TaskCountAdapter()

        binding.taskTypeRv.apply {
            adapter = taskCountAdapter
            setHasFixedSize(false)
        }

        viewModel.getUserData()?.let {
            binding.nameText.text = it.username
            binding.emailText.text = it.email
            binding.profileImg.setProfileImage(it.profileImage)
        }

        binding.logout.setOnClickListener {
            viewModel.onLogoutPressed()
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.taskCounts.collect {
                taskCountAdapter.submitList(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.shouldLogout.collect {
                if (it)
                    goBackToAuth()
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.taskStates.collect {
                setTaskStateCounts(it)
            }
        }

    }

    private fun setTaskStateCounts(map: Map<ProfileViewModel.ProfileScreenTaskCount, Int>) {
        binding.apply {
            completedTaskCount.text = map[COMPLETED].toString()
            IncompleteTaskCount.text = map[INCOMPLETE].toString()
            totalTaskCount.text = map[TOTAL].toString()
        }
    }

    private fun goBackToAuth() {
        Intent(requireContext(), AuthActivity::class.java).also {
            startActivity(it)
            requireActivity().finish()
        }
    }


}