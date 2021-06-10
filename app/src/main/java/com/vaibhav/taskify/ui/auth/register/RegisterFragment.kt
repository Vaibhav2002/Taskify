package com.vaibhav.taskify.ui.auth.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.vaibhav.taskify.R
import com.vaibhav.taskify.databinding.FragmentLoginBinding
import com.vaibhav.taskify.databinding.FragmentRegisterBinding
import com.vaibhav.taskify.ui.auth.login.LoginViewModel
import com.vaibhav.taskify.util.GOOGLE_SIGN_IN
import com.vaibhav.taskify.util.Resource
import com.vaibhav.taskify.util.showErrorToast
import com.vaibhav.taskify.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_register) {

    private val binding by viewBinding(FragmentRegisterBinding::bind)
    private val viewModel: RegisterViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.emailInput.doOnTextChanged { text, _, _, _ ->
            viewModel.onEmailTextChange(email = text.toString())
        }
        binding.passwordInput.doOnTextChanged { text, _, _, _ ->
            viewModel.onPasswordTextChange(password = text.toString())
        }
        binding.usernameInput.doOnTextChanged { text, _, _, _ ->
            viewModel.onUsernameTextChange(username = text.toString())
        }

        binding.emailInput.setText(viewModel.email.value)
        binding.passwordInput.setText(viewModel.password.value)
        binding.usernameInput.setText(viewModel.username.value)


        binding.goToRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        binding.signInBtn.setOnClickListener {
            viewModel.onRegisterButtonPressed()
        }


        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.registerState.collect {
                binding.loadingAnim.isVisible = it is Resource.Loading
                when (it) {
                    is Resource.Empty -> Unit
                    is Resource.Error -> showErrorToast(it.message)
                    is Resource.Loading -> Unit
                    is Resource.Success -> {
                        navigateToHomeScreen()
                    }
                }
            }
        }


    }

    private fun navigateToHomeScreen() {
        findNavController().navigate(R.id.action_registerFragment_to_mainActivity)
        requireActivity().finish()
    }

}