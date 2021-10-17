package com.vaibhav.taskify.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import com.vaibhav.taskify.data.models.entity.TaskEntity
import timber.log.Timber

class TaskDiffUtil : DiffUtil.ItemCallback<TaskEntity>() {

    override fun areItemsTheSame(oldItem: TaskEntity, newItem: TaskEntity): Boolean {
        Timber.d((oldItem.task_id == newItem.task_id).toString())
        return oldItem.task_id == newItem.task_id
    }

    override fun areContentsTheSame(oldItem: TaskEntity, newItem: TaskEntity): Boolean {
        Timber.d((oldItem == newItem).toString())
        return oldItem == newItem
    }
}
