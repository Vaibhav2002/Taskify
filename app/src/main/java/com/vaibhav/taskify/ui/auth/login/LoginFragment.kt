package com.vaibhav.taskify.ui.auth.login

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.vaibhav.taskify.R
import com.vaibhav.taskify.databinding.FragmentLoginBinding
import com.vaibhav.taskify.util.GOOGLE_SIGN_IN
import com.vaibhav.taskify.util.Resource
import com.vaibhav.taskify.util.showErrorToast
import com.vaibhav.taskify.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import timber.log.Timber

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private val binding by viewBinding(FragmentLoginBinding::bind)
    private val viewModel: LoginViewModel by viewModels()

    private lateinit var gso: GoogleSignInOptions

    private lateinit var googleSignInClient: GoogleSignInClient


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeSocialMediaSignIn()

        binding.emailInput.doOnTextChanged { text, _, _, _ ->
            viewModel.onEmailTextChange(email = text.toString())
        }
        binding.passwordInput.doOnTextChanged { text, _, _, _ ->
            viewModel.onPasswordTextChange(password = text.toString())
        }

        binding.emailInput.setText(viewModel.email.value)
        binding.passwordInput.setText(viewModel.password.value)

        binding.goToRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        binding.signInBtn.setOnClickListener {
            viewModel.onLoginButtonPressed()
        }

        binding.googleButton.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, GOOGLE_SIGN_IN)
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.loginState.collect {
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

    private fun initializeSocialMediaSignIn() {
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)

    }

    private fun navigateToHomeScreen() {
        findNavController().navigate(R.id.action_loginFragment_to_mainActivity)
        requireActivity().finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Timber.d("${resultCode == RESULT_OK}")
        if (requestCode == GOOGLE_SIGN_IN) {
            Timber.d(data.toString())
            data?.let { viewModel.onGoogleLoginPressed(it) }
        }
    }
}