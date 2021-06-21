package com.vaibhav.taskify.ui.addTaskScreen.addTask

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.vaibhav.chatofy.util.setMarginTop
import com.vaibhav.taskify.R
import com.vaibhav.taskify.databinding.FragmentAddTaskDetailBinding
import com.vaibhav.taskify.ui.addTaskScreen.AddTaskActivity
import com.vaibhav.taskify.ui.addTaskScreen.AddTaskViewModel
import com.vaibhav.taskify.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import timber.log.Timber

@AndroidEntryPoint
class AddTaskDetailFragment : Fragment(R.layout.fragment_add_task_detail) {

    private val binding by viewBinding(FragmentAddTaskDetailBinding::bind)
    private val viewModel: AddTaskViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, insets ->
            binding.backArrow.setMarginTop(insets.systemWindowInsetTop)
            insets.consumeSystemWindowInsets()
        }
        binding.taskImage.setLargeImage(R.drawable.task_illustration)


        binding.taskTitle.setText(viewModel.screenState.value.title)
        binding.taskDescription.setText(viewModel.screenState.value.description)
        if (viewModel.screenState.value.duration != 0L)
            binding.taskDuration.setDuration(viewModel.screenState.value.duration)

        binding.backArrow.setOnClickListener {
            requireActivity().finish()
        }

        binding.taskDuration.setOnClickListener {
            findNavController().navigate(R.id.action_addTaskDetailFragment_to_taskTimeSelectBottomSheetFragment)
        }

        binding.saveFab.setOnClickListener {
            viewModel.onSaveClicked()
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.addTaskState.collect {
                binding.loadingAnim.isVisible = it is Resource.Loading
                when (it) {
                    is Resource.Empty -> Unit
                    is Resource.Error -> {
                        it.errorType?.let { error ->
                            (requireActivity() as AddTaskActivity).showErrorDialog(error)
                        } ?: requireContext().showToast(it.message)
                    }
                    is Resource.Loading -> Unit
                    is Resource.Success -> {
                        requireActivity().finish()
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.screenState.collect {
                binding.apply {
                    if (it.titleError.isNotEmpty())
                        taskTitle.error = it.titleError
                    if (it.descriptionError.isNotEmpty())
                        taskDescription.error = it.descriptionError
                    if (it.durationError.isNotEmpty())
                        taskDuration.error = it.durationError
                    Timber.d(it.duration.toString())
                    if (it.duration != 0L)
                        binding.taskDuration.setDuration(it.duration)
                }
            }
        }


        val arrayAdapter = ArrayAdapter.createFromResource(
            requireContext(), R.array.spinner_item, R.layout.custom_spinner_item
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