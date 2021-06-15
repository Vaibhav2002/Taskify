package com.vaibhav.taskify.util

import com.vaibhav.taskify.R

const val GOOGLE_SIGN_IN = 1001
const val DURATION = "Duration"
const val TASK = "TASK"
const val FROM_NOTIFICATION = 32

enum class TaskType(val imageId: Int, val tagBackground: Int, val color: Int) {
    HOME(R.drawable.home_task, R.drawable.home_task_background, R.color.home_task_color1),
    WORK(R.drawable.work_task, R.drawable.work_task_background, R.color.work_task_color1),
    GYM(R.drawable.gym_task, R.drawable.gym_task_background, R.color.gym_task_color1),
    STUDY(R.drawable.study_task, R.drawable.study_task_background, R.color.study_task_color1)
}

enum class TopLevelScreens(val fragmentId: Int) {
    HOME(R.id.homeFragment), PROFILE(R.id.profileFragment),
    STATS(R.id.statsFragment), ABOUt(R.id.aboutFragment),
    TIMER(R.id.timerFragment)
}

enum class TaskState {
    RUNNING, PAUSED, COMPLETED, NOT_STARTED
}

enum class StopWatchFor {
    UPCOMING, RUNNING, PAUSED
}