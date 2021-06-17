package com.vaibhav.taskify.service

import androidx.lifecycle.MutableLiveData

enum class TimerState {
    START, STOP
}

object ServiceUtil {

    val timeLeft = MutableLiveData<Long>(0)


    val timerState = MutableLiveData<TimerState?>(null)
}

