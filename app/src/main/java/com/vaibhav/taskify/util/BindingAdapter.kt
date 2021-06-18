package com.vaibhav.taskify.util

import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import coil.load
import com.vaibhav.taskify.data.models.entity.TaskEntity
import java.time.Duration
import java.util.*

@BindingAdapter("setTaskDuration")
fun TextView.setTaskDuration(task: TaskEntity) {
    val timeLeft = if (task.state == TaskState.COMPLETED) task.duration else task.timeLeft
    text = timeLeft.formatDuration()

}

@BindingAdapter("setTaskDuration")
fun TextView.setDuration(timeLeft: Long) {
    text = timeLeft.formatDuration()
}


@BindingAdapter("setLargeImage")
fun ImageView.setLargeImage(image: Int) {
    this.load(image) {
        crossfade(true)
    }
}


@BindingAdapter("setTaskDuration")
fun EditText.setTaskDuration(timeLeft: Long) {
    setText(timeLeft.formatDuration())
}

@BindingAdapter("setTimeLeft")
fun TextView.setTimeLeft(timeLeft: Long) {
    val duration = Duration.ofMillis(timeLeft)
    val seconds = duration.seconds
    text = String.format("%d:%02d:%02d", seconds / 3600, (seconds % 3600) / 60, (seconds % 60));
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