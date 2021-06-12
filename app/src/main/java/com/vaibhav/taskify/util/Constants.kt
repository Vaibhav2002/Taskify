package com.vaibhav.taskify.util

import com.vaibhav.taskify.R

const val GOOGLE_SIGN_IN = 1001

enum class TaskType(resourceId: Int) {
    HOME(R.drawable.home_task),
    WORK(R.drawable.work_task),
    GYM(R.drawable.gym_task),
    STUDY(R.drawable.study_task)
}