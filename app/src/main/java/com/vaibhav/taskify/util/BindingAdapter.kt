package com.vaibhav.taskify.util

import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.vaibhav.taskify.data.models.entity.TaskEntity
import java.time.Duration
import java.util.*

@BindingAdapter("setTaskDuration")
fun TextView.setTaskDuration(task: TaskEntity) {
    val timeLeft = if (task.state == TaskState.COMPLETED) task.duration else task.timeLeft
    val duration = Duration.ofMillis(timeLeft)
    var seconds = duration.seconds
    val hours = seconds / 60 / 60
    seconds %= 60 * 60
    val minutes = seconds / 60
    seconds %= 60
    text = "${hours}hrs ${minutes}min ${seconds}sec"
}

@BindingAdapter("setTimeLeft")
fun TextView.setTimeLeft(timeLeft: Long) {
    val duration = Duration.ofMillis(timeLeft)
    var seconds = duration.seconds
    val hours = seconds / 60 / 60
    seconds %= 60 * 60
    val minutes = seconds / 60
    seconds %= 60
    text = "${hours}:${minutes}:${seconds}"
}


@BindingAdapter("setBackground")
fun TextView.setBackground(taskType: TaskType) {
    background = resources.getDrawable(taskType.tagBackground)
}

@BindingAdapter("setTagImage")
fun ImageView.setTagImage(taskType: TaskType) {
    setImageResource(taskType.imageId)
}

@BindingAdapter("setTaskState")
fun TextView.setTaskState(taskState: TaskState) {
    text = taskState.name.lowercase()
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
}

@BindingAdapter("setTaskStateVisibility")
fun TextView.setTaskStateVisibility(taskState: TaskState) {
    isVisible = taskState == TaskState.COMPLETED || taskState == TaskState.PAUSED
}