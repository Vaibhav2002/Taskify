package com.vaibhav.taskify.ui.addTaskScreen

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.widget.doOnTextChanged
import com.vaibhav.chatofy.util.makeStatusBarTransparent
import com.vaibhav.chatofy.util.setMarginTop
import com.vaibhav.chatofy.util.viewBinding
import com.vaibhav.taskify.R
import com.vaibhav.taskify.databinding.ActivityAddTaskBinding
import com.vaibhav.taskify.util.TaskType
import com.vaibhav.taskify.util.setTaskDuration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTaskActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityAddTaskBinding::inflate)
    private val viewModel: AddTaskViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)
        makeStatusBarTransparent()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, insets ->
            binding.backArrow.setMarginTop(insets.systemWindowInsetTop)
            insets.consumeSystemWindowInsets()
        }

        binding.taskTitle.setText(viewModel.screenState.value.title)
        binding.taskDescription.setText(viewModel.screenState.value.description)
        if (viewModel.screenState.value.duration != 0L)
            binding.taskDuration.setTaskDuration(viewModel.screenState.value.duration)

        val arrayAdapter = ArrayAdapter.createFromResource(
            this, R.array.spinner_item, R.layout.custom_spinner_item
        )
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.taskCategorySpinner.adapter = arrayAdapter

        binding.taskTitle.doOnTextChanged { text, _, _, _ ->
            viewModel.onTitleChanged(text.toString())
        }
        binding.taskDescription.doOnTextChanged { text, _, _, _ ->
            viewModel.onDescriptionChanged(text.toString())
        }

        binding.taskCategorySpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val taskCategory =
                        TaskType.valueOf(arrayAdapter.getItem(position).toString().uppercase())
                    binding.taskCategoryColor.setBackgroundResource(taskCategory.tagBackground)
                    viewModel.onCategoryChanged(taskCategory)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) = Unit
            }

    }

}