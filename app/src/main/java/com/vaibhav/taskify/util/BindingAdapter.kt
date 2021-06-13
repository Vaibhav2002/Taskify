package com.vaibhav.taskify.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.vaibhav.taskify.data.models.entity.TaskEntity
import java.util.*

@BindingAdapter("setTaskDuration")
fun TextView.setTaskDuration(task: TaskEntity) {
    val cal1 = Calendar.getInstance()
    cal1.timeInMillis = task.start_time
    var startTime = "${cal1.time.hours}:${cal1.time.minutes}"
    if (cal1.time.minutes == 0)
        startTime += "0"
    cal1.timeInMillis = task.end_time
    var endTime = "${cal1.time.hours}:${cal1.time.minutes}"
    if (cal1.time.minutes == 0)
        endTime += "0"
    text = "$startTime - $endTime"
}

@BindingAdapter("setBackground")
fun TextView.setBackground(taskType: TaskType) {
    background = resources.getDrawable(taskType.tagBackground)
}

@BindingAdapter("setTagImage")
fun ImageView.setTagImage(taskType: TaskType) {
    setImageResource(taskType.imageId)
}