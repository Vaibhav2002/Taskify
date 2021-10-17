package com.vaibhav.taskify.ui.auth.register

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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
import com.vaibhav.taskify.databinding.FragmentRegisterBinding
import com.vaibhav.taskify.ui.auth.AuthActivity
import com.vaibhav.taskify.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_register) {

    private val binding by viewBinding(FragmentRegisterBinding::bind)
    private val viewModel: RegisterViewModel by viewModels()
    private lateinit var googleSignUpLauncher: ActivityResultLauncher<Intent>

    private lateinit var gso: GoogleSignInOptions

    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeSocialMediaSignIn()

        binding.registerImage.setLargeImage(R.drawable.login_illustration)

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

        googleSignUpLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK)
                    handleGoogleSignUp(it.data)
            }

        binding.goToLogin.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
        binding.signInBtn.setOnClickListener {
            viewModel.onRegisterButtonPressed()
        }

        binding.googleButton.setOnClickListener {
            val signUpIntent = googleSignInClient.signInIntent
            googleSignUpLauncher.launch(signUpIntent)
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.registerState.collect {
                binding.loadingAnim.isVisible = it is Resource.Loading
                when (it) {
                    is Resource.Empty -> Unit
                    is Resource.Error -> {
                        if (it.errorType == ErrorTYpe.NO_INTERNET)
                            (requireActivity() as AuthActivity).showErrorDialog(it.errorType)
                        else
                            requireContext().showToast(it.message)
                    }
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
        findNavController().navigate(R.id.action_registerFragment_to_mainActivity)
        requireActivity().finish()
    }

    private fun handleGoogleSignUp(data: Intent?) {
        data?.let { viewModel.onGoogleRegisterPressed(it) }
    }
}
