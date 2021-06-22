package com.vaibhav.taskify.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.vaibhav.taskify.data.models.OnBoarding
import com.vaibhav.taskify.databinding.OnBoardingDesignBinding

class OnBoardingAdapter(
    private val list: List<OnBoarding>,
    private val onContinueClicked: () -> Unit
) : RecyclerView.Adapter<OnBoardingAdapter.onBoardingViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): onBoardingViewHolder {
        val binding = LayoutInflater.from(parent.context)
        return onBoardingViewHolder(OnBoardingDesignBinding.inflate(binding, parent, false))
    }

    override fun onBindViewHolder(holder: onBoardingViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class onBoardingViewHolder(private val binding: OnBoardingDesignBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.continueButton.setOnClickListener {
                onContinueClicked()
            }
        }

        fun bind(onBoarding: OnBoarding) {
            binding.continueButton.isVisible = onBoarding.isLastPage
            binding.onBoarding = onBoarding
        }


    }

}