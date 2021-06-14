package com.vaibhav.taskify.service

import androidx.lifecycle.MutableLiveData

object ServiceTimer {

    val _timeLeft = MutableLiveData<Long>(0)
}