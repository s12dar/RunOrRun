package com.lyvetech.runorrun.services

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_LOW
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import com.lyvetech.runorrun.R
import com.lyvetech.runorrun.ui.MainActivity
import com.lyvetech.runorrun.ui.fragments.TrackingFragment
import com.lyvetech.runorrun.utils.Constants.Companion.ACTION_PAUSE_SERVICE
import com.lyvetech.runorrun.utils.Constants.Companion.ACTION_SHOW_TRACKING_FRAGMENT
import com.lyvetech.runorrun.utils.Constants.Companion.ACTION_START_OR_RESUME_SERVICE
import com.lyvetech.runorrun.utils.Constants.Companion.ACTION_STOP_SERVICE
import com.lyvetech.runorrun.utils.Constants.Companion.NOTIFICATION_CHANNEL_ID
import com.lyvetech.runorrun.utils.Constants.Companion.NOTIFICATION_CHANNEL_NAME
import com.lyvetech.runorrun.utils.Constants.Companion.NOTIFICATION_ID

class TrackingService : LifecycleService() {
    private var TAG = TrackingService::class.qualifiedName
    private var isFirstRun = true

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            when (it.action) {
                ACTION_START_OR_RESUME_SERVICE -> {
                    if (isFirstRun) {
                        startForegroundService()
                        isFirstRun = false
                    } else {
                        Log.d(TAG, "Resuming service")
                    }
                    Log.d(TAG, "Started or resumed service")
                }
                ACTION_PAUSE_SERVICE -> {
                    Log.d(TAG, "Started or resumed service")
                }
                ACTION_STOP_SERVICE -> {
                    Log.d(TAG, "Started or resumed service")
                }
                else -> {}
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun getMainActivityPendingIntent() = PendingIntent.getActivity(
        this,
        0,
        Intent(this, MainActivity::class.java).also {
            it.action = ACTION_SHOW_TRACKING_FRAGMENT
        },
        FLAG_UPDATE_CURRENT
    )

    private fun startForegroundService() {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }

        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setAutoCancel(false)
            .setOngoing(true)
            .setSmallIcon(R.drawable.ic_run_24dp)
            .setContentTitle("00:00:00")
            .setContentIntent(getMainActivityPendingIntent())

        startForeground(NOTIFICATION_ID, notificationBuilder.build())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(channel)
    }
}