package com.vaibhav.taskify.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vaibhav.taskify.data.models.entity.TaskEntity
import com.vaibhav.taskify.databinding.TaskItemLayoutBinding

class TaskAdapter(private val onTaskClicked: (TaskEntity) -> Unit) :
    ListAdapter<TaskEntity, TaskAdapter.TaskViewHolder>(TaskDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding =
            TaskItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = currentList[position]
        holder.bind(task)
    }

    inner class TaskViewHolder(private val binding: TaskItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onTaskClicked(currentList[adapterPosition])
            }
        }

        fun bind(task: TaskEntity) {
            binding.task = task
        }

    }


}