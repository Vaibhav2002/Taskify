package com.vaibhav.taskify.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.vaibhav.chatofy.util.makeStatusBarTransparent
import com.vaibhav.chatofy.util.viewBinding
import com.vaibhav.taskify.R
import com.vaibhav.taskify.databinding.ActivityAuthBinding
import com.vaibhav.taskify.databinding.ActivityMainBinding
import com.vaibhav.taskify.util.GOOGLE_SIGN_IN
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {


    private val binding by viewBinding(ActivityAuthBinding::inflate)
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        navController = findNavController(R.id.fragment1)
        makeStatusBarTransparent()
    }

}