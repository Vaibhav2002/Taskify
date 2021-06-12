package com.vaibhav.taskify.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.vaibhav.chatofy.util.makeStatusBarTransparent
import com.vaibhav.chatofy.util.viewBinding
import com.vaibhav.taskify.R
import com.vaibhav.taskify.databinding.ActivityAuthBinding
import com.vaibhav.taskify.databinding.ActivityMainBinding
import com.vaibhav.taskify.ui.mainScreen.MainActivity
import com.vaibhav.taskify.util.GOOGLE_SIGN_IN
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityAuthBinding::inflate)
    private val viewModel:AuthViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        navController = findNavController(R.id.fragment1)
        makeStatusBarTransparent()
    }

    override fun onStart() {
        super.onStart()
        if(viewModel.isUserLoggedIn()){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }


}