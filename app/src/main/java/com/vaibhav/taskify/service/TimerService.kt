package com.vaibhav.taskify.service

import android.app.PendingIntent
import android.content.Intent
import android.os.CountDownTimer
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.vaibhav.taskify.data.models.entity.TaskEntity
import com.vaibhav.taskify.data.repo.PreferencesRepo
import com.vaibhav.taskify.data.repo.TaskRepo
import com.vaibhav.taskify.ui.mainScreen.MainActivity
import com.vaibhav.taskify.util.*
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class TimerService : LifecycleService() {

    @Inject
    lateinit var notificationHelper: NotificationHelper

    lateinit var timer: CountDownTimer

    @Inject
    lateinit var taskRepo: TaskRepo

    @Inject
    lateinit var preferencesRepo: PreferencesRepo

    lateinit var task: TaskEntity

    override fun onStart(intent: Intent?, startId: Int) {
        super.onStart(intent, startId)
        Timber.d("Service started ${this.hashCode()}")
        val duration = intent!!.getLongExtra(DURATION, 0L)
        task = intent.getSerializableExtra(TASK) as TaskEntity
        val pendingIntent = getPendingIntent(task)
        ServiceUtil.timerState.postValue(TimerState.START)
        timer = object : CountDownTimer(duration, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                Timber.d(millisUntilFinished.toString())
                val timerText = millisUntilFinished.formatDuration("%d:%02d:%02d left")
                ServiceUtil.timeLeft.postValue(millisUntilFinished)
                notificationHelper.showSilentNotification(timerText, task.task_title, pendingIntent)
            }

            override fun onFinish() {
                notificationHelper.showCompletedNotification(
                    "Completed task",
                    task.task_title,
                    pendingIntent
                )
                finishTask(task)
                ServiceUtil.timerState.postValue(TimerState.STOP)
                ServiceUtil.timeLeft.postValue(duration)
            }
        }
        timer.start()
    }

//    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        Timber.d("started running")
//        super.onStart(intent, startId)
//
//        return super.onStartCommand(intent, flags, startId)
//    }

    private fun getPendingIntent(task: TaskEntity): PendingIntent {
        val intent = Intent(this@TimerService, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        intent.putExtra(GO_TO_TIMER, true)
        intent.putExtra(TASK, task)
        return PendingIntent.getActivity(applicationContext, FROM_NOTIFICATION, intent, 0)
    }

    private fun finishTask(task: TaskEntity) = lifecycleScope.launchWhenStarted {
        task.timeLeft = 0
        task.state = TaskState.COMPLETED
        taskRepo.updateTask(task)
        preferencesRepo.setServiceRunning(false)
        stopSelf()
    }

    override fun onDestroy() {
        Timber.d("OnDestroy called ${this.hashCode()}")
        if (::timer.isInitialized)
            timer.cancel()
        super.onDestroy()
    }
}
