package com.vaibhav.taskify.ui.mainScreen.taskDetail

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.vaibhav.taskify.databinding.FragmentTaskDetailBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TaskDetailFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentTaskDetailBinding
    private val args by navArgs<TaskDetailFragmentArgs>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding = FragmentTaskDetailBinding.inflate(inflater, container, false)
        binding.task = args.task
        return binding.root
    }


}