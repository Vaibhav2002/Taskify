package com.vaibhav.taskify.ui.auth.gettingStarted

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.vaibhav.taskify.R
import com.vaibhav.taskify.databinding.FragmentGettingStartedBinding
import com.vaibhav.taskify.util.setLargeImage
import com.vaibhav.taskify.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GettingStartedFragment : Fragment(R.layout.fragment_getting_started) {

    private val binding by viewBinding(FragmentGettingStartedBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.gettingStartedImage.setLargeImage(R.drawable.getting_started_illustration)
        binding.signInBtn.setOnClickListener {
            findNavController().navigate(R.id.action_gettingStartedFragment_to_loginFragment)
        }
        binding.signUpBtn.setOnClickListener {
            findNavController().navigate(R.id.action_gettingStartedFragment_to_registerFragment)
        }
    }
}
