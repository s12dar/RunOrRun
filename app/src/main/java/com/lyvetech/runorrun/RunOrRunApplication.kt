package com.lyvetech.runorrun

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RunOrRunApplication: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}