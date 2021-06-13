package com.vaibhav.taskify.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vaibhav.taskify.data.models.entity.TaskEntity
import com.vaibhav.taskify.databinding.RunningTaskLayoutBinding

class OnGoingTaskAdapter(private val onTaskClicked: (TaskEntity) -> Unit) :
    ListAdapter<TaskEntity, OnGoingTaskAdapter.OnGoingViewHolder>(TaskDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnGoingViewHolder {
        val binding =
            RunningTaskLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OnGoingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OnGoingViewHolder, position: Int) {
        val task = currentList[position]
        holder.bind(task)
    }

    inner class OnGoingViewHolder(private val binding: RunningTaskLayoutBinding) :
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