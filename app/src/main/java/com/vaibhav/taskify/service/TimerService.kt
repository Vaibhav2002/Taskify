package com.vaibhav.taskify.service

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.CountDownTimer
import android.os.IBinder
import com.vaibhav.taskify.data.models.entity.TaskEntity
import com.vaibhav.taskify.ui.mainScreen.MainActivity
import com.vaibhav.taskify.util.DURATION
import com.vaibhav.taskify.util.FROM_NOTIFICATION
import com.vaibhav.taskify.util.NotificationHelper
import com.vaibhav.taskify.util.TASK
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.time.Duration
import javax.inject.Inject

@AndroidEntryPoint
class TimerService : Service() {

    @Inject
    lateinit var notificationHelper: NotificationHelper


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Timber.d("started running")
        val duration = intent!!.getLongExtra(DURATION, 0L)
        val task = intent.getSerializableExtra(TASK) as TaskEntity
        val intent = Intent(this@TimerService, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        val pendingIntent =
            PendingIntent.getActivity(applicationContext, FROM_NOTIFICATION, intent, 0)
        ServiceTimer.timerState.postValue(TimerState.START)
        val timer = object : CountDownTimer(duration, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                Timber.d(millisUntilFinished.toString())
                val durationVal = Duration.ofMillis(millisUntilFinished)
                val timerText = getFormattedTimeString(durationVal)
                Timber.d(timerText)
                ServiceTimer.timeLeft.postValue(millisUntilFinished)
                notificationHelper.showNotification(timerText, task.task_title, pendingIntent)
            }

            override fun onFinish() {
                notificationHelper.showNotification(
                    "Completed task",
                    task.task_title,
                    pendingIntent
                )
                ServiceTimer.timerState.postValue(TimerState.STOP)
                ServiceTimer.timeLeft.postValue(duration)
            }
        }
        timer.start()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? = null

//    override fun onBind(intent: Intent): IBinder? {
//
//        return super.onBind(intent)
//    }

//    override fun onHandleIntent(intent: Intent?) {
//
//
//    }

    private fun getFormattedTimeString(duration: Duration): String {
        var seconds = duration.seconds
        val hours = seconds / 60 / 60
        seconds %= 60 * 60
        val minutes = seconds / 60
        seconds %= 60
        return "${hours}:${minutes}:${seconds}"
    }
}