package com.google.firebase.inappmessaging.logger

import com.datadog.android.log.Logger
import com.google.firebase.inappmessaging.BuildConfig

class FirebaseLogger {
    private val logger by lazy {
        Logger.Builder()
            .setNetworkInfoEnabled(true)
            .setLogcatLogsEnabled(true)
            .setLoggerName("firebaseLogger")
            .build()
    }

    fun logMessage(tag: String, message: String, throwable: Throwable? = null) {
        logger.addAttribute("app.flavor", BuildConfig.FLAVOR)
        logger.addAttribute("app.build", BuildConfig.BUILD_TYPE)
        logger.addAttribute("app.platform", "Android")
        logger.addAttribute("app.version", BuildConfig.BUILD_NUMBER)
        logger.i(message = message, throwable = throwable, attributes = mapOf("tag" to tag))
    }
}