package com.vaibhav.taskify.util

import com.vaibhav.taskify.R
import java.util.*

const val GOOGLE_SIGN_IN = 1001
const val DURATION = "Duration"
const val TASK = "TASK"
const val FROM_NOTIFICATION = 32
const val GO_TO_TIMER = "FROM_NOTIFICATION"
const val SHOW_ERROR_DIALOG = "ERROR_DIALOG"

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

data class TaskStateCount(
    var completed: Int = 0,
    var total: Int = 0,
    var incomplete: Int = total - completed,
)

enum class StopWatchFor {
    UPCOMING, RUNNING, PAUSED
}


enum class ErrorTYpe(val title: Int, val message: Int, val image: Int) {
    NO_INTERNET(R.string.no_internet_title, R.string.no_internet_message, R.drawable.no_internet),
    NO_TASKS(R.string.no_tasks_title, R.string.no_tasks_message, R.drawable.no_tasks),
    NO_TASKS_LAST_WEEK(
        R.string.no_tasks_title,
        R.string.no_tasks_last_week_message,
        R.drawable.no_tasks
    ),
    UNKNOWN(R.string.unknown_error_title, R.string.unknown_error_message, R.drawable.no_internet)
}

enum class DAYS(index: Int) {
    SUNDAY(1), MONDAY(2), TUESDAY(3), WEDNESDAY(4), THURSDAY(5), FRIDAY(6), SATURDAY(7);

    companion object {
        fun getDayFromNumber(d: Int) = when (d) {
            1 -> SUNDAY
            2 -> MONDAY
            3 -> TUESDAY
            4 -> WEDNESDAY
            5 -> THURSDAY
            6 -> FRIDAY
            7 -> SATURDAY
            else -> SUNDAY
        }
    }

    fun getShortFormFromNumber() = when (this) {
        SUNDAY -> getFirstChars()
        MONDAY -> getFirstChars()
        TUESDAY -> getFirstChars()
        WEDNESDAY -> getFirstChars()
        THURSDAY -> getTwoChars()
        FRIDAY -> getFirstChars()
        SATURDAY -> getTwoChars()
        else -> getTwoChars()
    }

    fun getFullName() = this.name.lowercase().capitalize()


    private fun getFirstChars() = this.name.substring(0, 1)

    private fun getTwoChars() = this.name.substring(0, 2).lowercase()
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }

}