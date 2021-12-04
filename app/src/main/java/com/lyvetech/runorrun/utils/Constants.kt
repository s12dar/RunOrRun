package com.lyvetech.runorrun.utils

class Constants {
    companion object {

        /**
         * DATABASE
         */
        const val RUNNING_DATABASE_NAME = "running_db"

        /**
         * FRAGMENTS
         */
        const val REQUEST_LOCATION_PERMISSION = 0

        /**
         * LOCATION SERVICE
         */
        const val ACTION_START_OR_RESUME_SERVICE = "ACTION_START_OR_RESUME_SERVICE"
        const val ACTION_PAUSE_SERVICE = "ACTION_PAUSE_SERVICE"
        const val ACTION_STOP_SERVICE = "ACTION_STOP_SERVICE"
        const val NOTIFICATION_CHANNEL_ID = "tracking_channel"
        const val NOTIFICATION_CHANNEL_NAME = "Tracking"
        const val NOTIFICATION_ID = 1
        const val ACTION_SHOW_TRACKING_FRAGMENT = "ACTION_SHOW_TRACKING_FRAGMENT"
        const val LOCATION_UPDATE_INTERVAL = 5000L
        const val FASTEST_LOCATION_INTERVAL = 2000L
    }
}