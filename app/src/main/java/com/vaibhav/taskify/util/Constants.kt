package com.vaibhav.taskify.util

import com.vaibhav.taskify.R

const val GOOGLE_SIGN_IN = 1001

enum class TaskType(val imageId: Int, val tagBackground: Int) {
    HOME(R.drawable.home_task, R.drawable.home_task_background),
    WORK(R.drawable.work_task, R.drawable.work_task_background),
    GYM(R.drawable.gym_task, R.drawable.gym_task_background),
    STUDY(R.drawable.study_task, R.drawable.study_task_background)
}

enum class TopLevelScreens(val fragmentId: Int) {
    HOME(R.id.homeFragment), PROFILE(R.id.profileFragment),
    STATS(R.id.statsFragment), ABOUt(R.id.aboutFragment)
}