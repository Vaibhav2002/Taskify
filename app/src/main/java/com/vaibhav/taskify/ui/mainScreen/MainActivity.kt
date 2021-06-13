package com.vaibhav.taskify.ui.mainScreen

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import coil.load
import com.vaibhav.chatofy.util.viewBinding
import com.vaibhav.taskify.R
import com.vaibhav.taskify.databinding.ActivityMainBinding
import com.vaibhav.taskify.databinding.DrawerMenuBinding
import com.vaibhav.taskify.util.TopLevelScreens
import com.vaibhav.taskify.util.setUsable
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)
    private lateinit var navController: NavController
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        navController = findNavController(R.id.fragment2)

        viewModel.fetchAllTasks()

        initializeDrawerLayout()
        setSupportActionBar(binding.activityMainContent.toolbar)
        binding.activityMainContent.menuIcon.setOnClickListener {
            binding.drawer.openDrawer()
        }
        navController.addOnDestinationChangedListener { _, destination, _ ->
            Timber.d("NEw dest ${destination.id}")
            binding.drawer.closeDrawer()
            setNavButtons()
        }
    }

    private fun initializeDrawerLayout() {

        val drawerBinding = DrawerMenuBinding.bind(binding.drawer.menuView)
        drawerBinding.apply {
            userNameText.text = viewModel.user?.username
            userEmailText.text = viewModel.user?.email
            avatarImage.load(viewModel.user?.profileImage)
            homeItem.setOnClickListener {
                handleNavigation(TopLevelScreens.HOME)
            }
            profileItem.setOnClickListener {
                handleNavigation(TopLevelScreens.PROFILE)
            }
            aboutItem.setOnClickListener {
                handleNavigation(TopLevelScreens.ABOUt)
            }
            statsItem.setOnClickListener {
                handleNavigation(TopLevelScreens.STATS)
            }
        }


        binding.drawer.setDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) = Unit

            override fun onDrawerOpened(drawerView: View) {
            }

            override fun onDrawerClosed(drawerView: View) = Unit

            override fun onDrawerStateChanged(newState: Int) = Unit
        })
    }

    private fun handleNavigation(screen: TopLevelScreens) {
        if (navController.currentDestination?.id == screen.fragmentId)
            return
        val navigationId = when (screen) {
            TopLevelScreens.HOME -> R.id.homeFragmentGlobal
            TopLevelScreens.PROFILE -> R.id.profileFragmentGlobal
            TopLevelScreens.STATS -> R.id.statsFragmentGlobal
            TopLevelScreens.ABOUt -> R.id.aboutFragmentGlobal
        }
        Timber.d(navigationId.toString())
        navController.popBackStack()
        navController.navigate(navigationId)
    }

    private fun setNavButtons() {
        val openDrawerView = DrawerMenuBinding.bind(binding.drawer.menuView)
        val currentFragment = navController.currentDestination?.id ?: 0
        Timber.d("Current $currentFragment")
        openDrawerView.homeItem.setUsable(currentFragment, TopLevelScreens.HOME)
        openDrawerView.profileItem.setUsable(currentFragment, TopLevelScreens.PROFILE)
        openDrawerView.aboutItem.setUsable(currentFragment, TopLevelScreens.ABOUt)
        openDrawerView.statsItem.setUsable(currentFragment, TopLevelScreens.STATS)
    }


}


