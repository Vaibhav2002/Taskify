package com.vaibhav.taskify.util

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.widget.*
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import coil.load
import com.bumptech.glide.Glide
import com.vaibhav.taskify.R
import com.vaibhav.taskify.data.models.entity.TaskEntity
import timber.log.Timber
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
    text = String.format("%d:%02d:%02d", seconds / 3600, (seconds % 3600) / 60, (seconds % 60))
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

@BindingAdapter("setBarHeight")
fun FrameLayout.setBarHeight(height: Int) {
    val percent = (height.toDouble() / 15.0 * 100).toInt()
    Timber.d("percent $percent")
    val params = layoutParams
    val maxHeight = resources.getDimension(R.dimen.bar_height).toInt()
    params.height = maxHeight * percent / 100
    Timber.d("finalheight ${maxHeight * percent / 100}")
    layoutParams = params
}

@SuppressLint("UseCompatLoadingForDrawables")
@BindingAdapter("setBarBackground")
fun FrameLayout.setBarBackground(isPressed: Boolean) {
    backgroundTintList = ColorStateList.valueOf(
        if (isPressed) resources.getColor(R.color.barChartClickColor) else resources.getColor(R.color.barChartColor)
    )
}

@BindingAdapter("setBarCount")
fun TextView.setBarCount(count: Int) {
    text = "$count"
}

@BindingAdapter("setUserProfileImage")
fun ImageView.setProfileImage(url: String) {
    val errorImage = R.drawable.avatar_male
    Glide.with(context)
        .load(url)
        .error(errorImage)
        .centerCrop()
        .into(this)
}

@BindingAdapter("setErrorImage")
fun ImageView.setErrorImage(uri: Int) {
    Glide.with(context)
        .load(uri)
        .into(this)
}

@BindingAdapter("setTaskTypeBackground")
fun LinearLayout.setTaskTypeBg(taskType: TaskType) {
    background = resources.getDrawable(taskType.tagBackground)
}

@BindingAdapter("setTaskTypeBg")
fun TextView.setTaskTypeBg(taskType: TaskType) {
    background = resources.getDrawable(taskType.tagBackground)
}
