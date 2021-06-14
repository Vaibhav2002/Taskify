package com.vaibhav.taskify.ui.mainScreen

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import coil.load
import com.vaibhav.chatofy.util.viewBinding
import com.vaibhav.taskify.R
import com.vaibhav.taskify.data.models.entity.TaskEntity
import com.vaibhav.taskify.databinding.ActivityMainBinding
import com.vaibhav.taskify.databinding.DrawerMenuBinding
import com.vaibhav.taskify.service.ServiceTimer
import com.vaibhav.taskify.service.TimerService
import com.vaibhav.taskify.util.DURATION
import com.vaibhav.taskify.util.StopWatchFor
import com.vaibhav.taskify.util.TopLevelScreens
import com.vaibhav.taskify.util.setUsable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)
    private lateinit var navController: NavController
    private val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var timer: ServiceTimer

//    private val connection = object : ServiceConnection {
//        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
//            viewModel.isServiceRunning = true
//        }
//
//        override fun onServiceDisconnected(name: ComponentName?) {
//            viewModel.isServiceRunning = false
//        }
//    }


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

        lifecycleScope.launchWhenStarted {
            viewModel.runningTask.collect {
//                if (it.isNotEmpty()) {
//                    startService(it[0])
//                } else {
//                    stopService()
//                }
            }
        }

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            Timber.d("DEstination chnaged")
//            if(destination.id == R.id.timerFragment)
//                supportActionBar?.hide()
//            else
//                supportActionBar?.show()
        }

        timer._timeLeft.observe(this) {
            viewModel.setTimeLeft(it)
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

    }

    private fun handleNavigation(screen: TopLevelScreens) {
        if (navController.currentDestination?.id == screen.fragmentId)
            return
        val navigationId = when (screen) {
            TopLevelScreens.HOME -> R.id.homeFragmentGlobal
            TopLevelScreens.PROFILE -> R.id.profileFragmentGlobal
            TopLevelScreens.STATS -> R.id.statsFragmentGlobal
            TopLevelScreens.ABOUt -> R.id.aboutFragmentGlobal
            TopLevelScreens.TIMER -> R.id.timerFragmentGlobal
        }
        Timber.d(navigationId.toString())
        if (screen != TopLevelScreens.TIMER)
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


    private fun startService(task: TaskEntity) {
        Intent(this, TimerService::class.java).also {
            it.putExtra(DURATION, task.timeLeft)
            startService(it)
        }
    }

    private fun stopService() {
        if (viewModel.isServiceRunning) {
            Intent(this, TimerService::class.java).also {
                stopService(it)
            }
        }
    }

    fun showTimer(stopWatchFor: StopWatchFor, task: TaskEntity) {
        viewModel.task = task
        viewModel.stopWatchFor = stopWatchFor
        handleNavigation(TopLevelScreens.TIMER)
    }


}


