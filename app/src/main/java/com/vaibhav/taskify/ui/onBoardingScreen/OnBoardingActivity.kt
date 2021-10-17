package com.vaibhav.taskify.ui.onBoardingScreen

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.vaibhav.taskify.R
import com.vaibhav.taskify.data.models.OnBoarding
import com.vaibhav.taskify.databinding.ActivityOnBoardingBinding
import com.vaibhav.taskify.ui.adapters.OnBoardingAdapter
import com.vaibhav.taskify.ui.auth.AuthActivity
import com.vaibhav.taskify.util.makeStatusBarTransparent
import com.vaibhav.taskify.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class OnBoardingActivity : AppCompatActivity() {

    private val viewModel by viewModels<OnBoardingViewModel>()
    private val binding by viewBinding(ActivityOnBoardingBinding::inflate)
    private lateinit var onBoardingAdapter: OnBoardingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        makeStatusBarTransparent()
        val onBoardingList = listOf(
            OnBoarding(
                R.drawable.on_boarding_1,
                getString(R.string.onBoarding1Title),
                getString(R.string.onBoarding1Description),
            ),
            OnBoarding(
                R.drawable.on_boarding_2,
                getString(R.string.onBoarding2Title),
                getString(R.string.onBoarding2Description),
            ),
            OnBoarding(
                R.drawable.on_boarding_3,
                getString(R.string.onBoarding3Title),
                getString(R.string.onBoarding3Description),
                true
            )
        )

        onBoardingAdapter = OnBoardingAdapter(onBoardingList) {
            viewModel.onContinuePressed()
        }
        binding.onboardingViewpager.adapter = onBoardingAdapter
        binding.wormDotsIndicator.setViewPager2(binding.onboardingViewpager)

        lifecycleScope.launchWhenStarted {
            viewModel.navigateToAuth.collect {
                if (it)
                    navigateToAuth()
            }
        }
    }

    private fun navigateToAuth() {
        Intent(this, AuthActivity::class.java).also {
            startActivity(it)
            finish()
        }
    }
}
