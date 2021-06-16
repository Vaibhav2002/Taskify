package com.vaibhav.taskify.util

import android.widget.EditText
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
    val seconds = duration.seconds
    text =
        String.format(
            "%dhrs %02dmin %02dsec",
            seconds / 3600,
            (seconds % 3600) / 60,
            (seconds % 60)
        );

}


@BindingAdapter("setTaskDuration")
fun EditText.setTaskDuration(timeLeft: Long) {
    val duration = Duration.ofMillis(timeLeft)
    var seconds = duration.seconds
    setText(
        String.format(
            "%dhrs %02min %02sec",
            seconds / 3600,
            (seconds % 3600) / 60,
            (seconds % 60)
        )
    )
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