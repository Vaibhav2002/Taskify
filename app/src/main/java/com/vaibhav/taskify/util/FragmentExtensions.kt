package com.vaibhav.taskify.util

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.showErrorToast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}