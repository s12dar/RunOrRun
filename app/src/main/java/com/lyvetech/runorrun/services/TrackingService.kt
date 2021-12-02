package com.lyvetech.runorrun.services

import android.content.Intent
import android.util.Log
import androidx.lifecycle.LifecycleService
import com.lyvetech.runorrun.ui.fragments.TrackingFragment
import com.lyvetech.runorrun.utils.Constants.Companion.ACTION_PAUSE_SERVICE
import com.lyvetech.runorrun.utils.Constants.Companion.ACTION_START_OR_RESUME_SERVICE
import com.lyvetech.runorrun.utils.Constants.Companion.ACTION_STOP_SERVICE

class TrackingService : LifecycleService() {
    private var TAG = TrackingService::class.qualifiedName

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            when (it.action) {
                ACTION_START_OR_RESUME_SERVICE -> {
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
}