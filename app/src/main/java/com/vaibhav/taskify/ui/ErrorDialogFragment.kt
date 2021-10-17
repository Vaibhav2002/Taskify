package com.vaibhav.taskify.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.vaibhav.taskify.R
import com.vaibhav.taskify.databinding.FragmentErrorDialogBinding
import com.vaibhav.taskify.util.ErrorTYpe
import com.vaibhav.taskify.util.setErrorImage

class ErrorDialogFragment(private val errorTYpe: ErrorTYpe? = null) :
    DialogFragment(R.layout.fragment_error_dialog) {

    private lateinit var binding: FragmentErrorDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentErrorDialogBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        errorTYpe?.let {
            binding.apply {
                errorImage.setErrorImage(errorTYpe.image)
                errorTitle.text = getString(errorTYpe.title)
                errorDescription.text = getString(errorTYpe.message)
                closeBtn.setOnClickListener {
                    dismiss()
                }
            }
        } ?: dismiss()
    }
}
