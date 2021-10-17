package com.vaibhav.taskify.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vaibhav.taskify.data.models.TaskCount
import com.vaibhav.taskify.databinding.TaskTypeRecyclerItemBinding

class TaskCountAdapter :
    ListAdapter<TaskCount, TaskCountAdapter.TaskCountViewHolder>(TaskCountDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskCountViewHolder {
        val binding =
            TaskTypeRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskCountViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskCountViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class TaskCountViewHolder(private val binding: TaskTypeRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: TaskCount) {
            binding.task = data
        }
    }

    class TaskCountDiff : DiffUtil.ItemCallback<TaskCount>() {
        override fun areItemsTheSame(oldItem: TaskCount, newItem: TaskCount): Boolean {
            return oldItem.taskType == newItem.taskType
        }

        override fun areContentsTheSame(oldItem: TaskCount, newItem: TaskCount): Boolean {
            return oldItem == newItem
        }
    }
}
