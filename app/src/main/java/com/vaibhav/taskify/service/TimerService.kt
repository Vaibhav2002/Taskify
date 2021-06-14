package com.vaibhav.taskify.service

import android.app.IntentService
import android.app.PendingIntent
import android.content.Intent
import android.os.CountDownTimer
import com.vaibhav.taskify.ui.mainScreen.MainActivity
import com.vaibhav.taskify.util.DURATION
import com.vaibhav.taskify.util.FROM_NOTIFICATION
import com.vaibhav.taskify.util.NotificationHelper
import timber.log.Timber
import java.time.Duration
import javax.inject.Inject

class TimerService : IntentService("Timer") {

    @Inject
    lateinit var notificationHelper: NotificationHelper


    override fun onHandleIntent(intent: Intent?) {
        Timber.d("started running")
        val duration = intent!!.getLongExtra(DURATION, 0L)
        val durationVal = Duration.ofMillis(duration)
        val timerText = getFormattedTimeString(durationVal)
        val intent = Intent(this@TimerService, MainActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(applicationContext, FROM_NOTIFICATION, intent, 0)
        duration.let {
            object : CountDownTimer(duration, 1000L) {
                override fun onTick(millisUntilFinished: Long) {
                    Timber.d(millisUntilFinished.toString())
                    ServiceTimer._timeLeft.postValue(millisUntilFinished)
                    notificationHelper.showNotification(timerText, "", pendingIntent)
                }

                override fun onFinish() {
                    ServiceTimer._timeLeft.postValue(duration)
                }
            }.start()
        }
    }

    private fun getFormattedTimeString(duration: Duration): String {
        var seconds = duration.seconds
        val hours = seconds / 60 / 60
        seconds %= 60 * 60
        val minutes = seconds / 60
        seconds %= 60
        return "${hours}:${minutes}:${seconds}"
    }
}