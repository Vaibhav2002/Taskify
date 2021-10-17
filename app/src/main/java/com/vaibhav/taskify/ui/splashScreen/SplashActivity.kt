package com.vaibhav.taskify.ui.splashScreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.vaibhav.taskify.R
import com.vaibhav.taskify.ui.auth.AuthActivity
import com.vaibhav.taskify.ui.mainScreen.MainActivity
import com.vaibhav.taskify.ui.onBoardingScreen.OnBoardingActivity
import com.vaibhav.taskify.util.makeStatusBarTransparent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val viewModel by viewModels<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        makeStatusBarTransparent()
        Handler().postDelayed(
            {
                val intent = if (!viewModel.isOnBoardingComplete()) {
                    Intent(this, OnBoardingActivity::class.java)
                } else if (viewModel.isUserLogged()) {
                    Intent(this, MainActivity::class.java)
                } else {
                    Intent(this, AuthActivity::class.java)
                }

                startActivity(intent)
                finish()
            },
            2000L
        )
    }
}
