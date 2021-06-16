package com.vaibhav.taskify.service

import android.app.PendingIntent
import android.content.Intent
import android.os.CountDownTimer
import androidx.lifecycle.LifecycleService
import com.vaibhav.taskify.data.models.entity.TaskEntity
import com.vaibhav.taskify.ui.mainScreen.MainActivity
import com.vaibhav.taskify.util.*
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.time.Duration
import javax.inject.Inject

@AndroidEntryPoint
class TimerService : LifecycleService() {

    @Inject
    lateinit var notificationHelper: NotificationHelper

    lateinit var timer: CountDownTimer

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }

    override fun onStart(intent: Intent?, startId: Int) {
        super.onStart(intent, startId)
        Timber.d("started running")
        val duration = intent!!.getLongExtra(DURATION, 0L)
        val task = intent.getSerializableExtra(TASK) as TaskEntity

        val pendingIntent = getPendingIntent(task)
        ServiceTimer.timerState.postValue(TimerState.START)
        timer = object : CountDownTimer(duration, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                Timber.d(millisUntilFinished.toString())
                val durationVal = Duration.ofMillis(millisUntilFinished)
                val timerText = getFormattedTimeString(durationVal)
                ServiceTimer.timeLeft.postValue(millisUntilFinished)
                notificationHelper.showSilentNotification(timerText, task.task_title, pendingIntent)
            }

            override fun onFinish() {
                notificationHelper.showCompletedNotification(
                    "Completed task",
                    task.task_title,
                    pendingIntent
                )
                ServiceTimer.timerState.postValue(TimerState.STOP)
                ServiceTimer.timeLeft.postValue(duration)
            }
        }
        timer.start()
    }


    private fun getFormattedTimeString(duration: Duration): String {
        val seconds = duration.seconds
        return String.format("%d:%02d:%02d", seconds / 3600, (seconds % 3600) / 60, (seconds % 60));
    }

    private fun getPendingIntent(task: TaskEntity): PendingIntent {
        val intent = Intent(this@TimerService, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        intent.putExtra(GO_TO_TIMER, true)
        intent.putExtra(TASK, task)
        return PendingIntent.getActivity(applicationContext, FROM_NOTIFICATION, intent, 0)
    }


}