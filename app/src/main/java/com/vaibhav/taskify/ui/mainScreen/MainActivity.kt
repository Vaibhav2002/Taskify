package com.vaibhav.taskify.ui.mainScreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.view.menu.MenuAdapter
import androidx.appcompat.view.menu.MenuBuilder
import androidx.drawerlayout.widget.DrawerLayout
import com.vaibhav.chatofy.util.viewBinding
import com.vaibhav.taskify.R
import com.vaibhav.taskify.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initializeDrawerLayout()

        binding.activityMainContent.menuIcon.setOnClickListener {
            binding.drawer.openDrawer()
        }
    }

    private fun initializeDrawerLayout() {
    }
}