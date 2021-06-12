package com.vaibhav.taskify.ui.mainScreen

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.vaibhav.chatofy.util.viewBinding
import com.vaibhav.taskify.R
import com.vaibhav.taskify.databinding.ActivityMainBinding
import com.vaibhav.taskify.databinding.DrawerMenuBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)
    private lateinit var navController: NavController
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        navController = findNavController(R.id.fragment2)
        initializeDrawerLayout()

        binding.activityMainContent.menuIcon.setOnClickListener {
            binding.drawer.openDrawer()
        }
    }

    private fun initializeDrawerLayout() {
        binding.drawer.setDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) = Unit

            override fun onDrawerOpened(drawerView: View) {
                val drawerBinding = DrawerMenuBinding.bind(binding.drawer.menuView)
                drawerBinding.apply {
                    userNameText.text = viewModel.user?.username
                    userEmailText.text = viewModel.getUserData()?.email
                }
            }

            override fun onDrawerClosed(drawerView: View) = Unit

            override fun onDrawerStateChanged(newState: Int) = Unit
        })
    }
}