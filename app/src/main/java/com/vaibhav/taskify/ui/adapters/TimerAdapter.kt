package com.vaibhav.taskify.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vaibhav.taskify.databinding.TimerRecyclerItemBinding

class TimerAdapter(private val timeData: List<Int>) :
    RecyclerView.Adapter<TimerAdapter.TimerViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimerViewHolder {
        val binding =
            TimerRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TimerViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return timeData.size
    }

    override fun onBindViewHolder(holder: TimerViewHolder, position: Int) {
        holder.bind(timeData[position])
    }


    inner class TimerViewHolder(private val binding: TimerRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(timeData: Int) {
            binding.tvTime.text = if (timeData <= 9) "0$timeData" else "$timeData"
        }
    }

}