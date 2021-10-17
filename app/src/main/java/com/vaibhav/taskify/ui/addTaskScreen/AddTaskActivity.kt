package com.vaibhav.taskify.ui.addTaskScreen

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.vaibhav.taskify.util.makeStatusBarTransparent
import com.vaibhav.taskify.util.viewBinding
import com.vaibhav.taskify.R
import com.vaibhav.taskify.databinding.ActivityAddTaskBinding
import com.vaibhav.taskify.ui.ErrorDialogFragment
import com.vaibhav.taskify.util.ErrorTYpe
import com.vaibhav.taskify.util.SHOW_ERROR_DIALOG
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTaskActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityAddTaskBinding::inflate)
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        navController = findNavController(R.id.fragment3)
        makeStatusBarTransparent()
    }

    fun showErrorDialog(errorTYpe: ErrorTYpe) {
        ErrorDialogFragment(errorTYpe).show(supportFragmentManager, SHOW_ERROR_DIALOG)
    }
}
