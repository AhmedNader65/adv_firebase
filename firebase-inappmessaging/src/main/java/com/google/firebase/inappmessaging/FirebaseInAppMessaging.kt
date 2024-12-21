// Copyright 2018 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package com.google.firebase.inappmessaging

import com.google.android.gms.common.util.VisibleForTesting
import com.google.firebase.FirebaseApp
import com.google.firebase.inappmessaging.internal.*
import com.google.firebase.inappmessaging.internal.ForegroundNotifier.callForeground
import com.google.firebase.inappmessaging.internal.injection.qualifiers.AppForeground
import com.google.firebase.inappmessaging.internal.injection.qualifiers.ProgrammaticTrigger
import com.google.firebase.inappmessaging.internal.injection.scopes.FirebaseAppScope
import com.google.firebase.inappmessaging.logger.FirebaseLogger
import com.google.firebase.inappmessaging.model.TriggeredInAppMessage
import com.google.firebase.installations.FirebaseInstallationsApi
import io.reactivex.flowables.ConnectableFlowable
import okhttp3.internal.filterList
import java.util.concurrent.Executor
import javax.inject.Inject

/**
 * The entry point of the Firebase In App Messaging headless SDK.
 *
 *
 * Firebase In-App Messaging will automatically initialize, and start listening for events.
 *
 *
 * This feature uses a Firebase Installation ID token to:
 *
 *
 *  * identify the app instance
 *  * fetch messages from the Firebase backend
 *  * send usage metrics to the Firebase backend.
 *
 *
 *
 * To delete the Installation ID and the data associated with it, see [ ][FirebaseInstallationsApi.delete].
 */
@FirebaseAppScope
class FirebaseInAppMessaging @VisibleForTesting @Inject internal constructor(
    @param:AppForeground private val appForegroundEventFlowable: ConnectableFlowable<String>,
    private val inAppMessageStreamManager: InAppMessageStreamManager,
    @param:ProgrammaticTrigger private val programaticContextualTriggers: ProgramaticContextualTriggers,
    private val dataCollectionHelper: DataCollectionHelper,
    firebaseInstallations: FirebaseInstallationsApi,
    private val displayCallbacksFactory: DisplayCallbacksFactory,
    private val developerListenerManager: DeveloperListenerManager,
    private val firebaseLogger: FirebaseLogger
) {
    private var areMessagesSuppressed = false
    private var fiamDisplay: FirebaseInAppMessagingDisplay? = null
    private val TAG = "FirebaseInAppMessaging"

    fun logMessage(tag: String, message: String, throwable: Throwable? = null) {
        firebaseLogger.logMessage(tag, message, throwable)
    }

    fun runAdvanceFirebaseCheck(
        minArticleViews: Long
    ) {
        logMessage(TAG, "runAdvanceFirebaseCheck() called...")
        val unused = inAppMessageStreamManager
            .createFirebaseInAppMessageStream()
            .doOnError { logMessage(TAG, "runAdvanceFirebaseCheck():doOnError() called...", it) }
            .subscribe(
                { content: List<TriggeredInAppMessage> ->
                    triggerInAppMessage(
                        minArticleViews,
                        content
                    )
                },
                { throwable ->
                    logMessage(
                        TAG,
                        "runAdvanceFirebaseCheck():onError() called...",
                        throwable
                    )
                })
    }

    fun callNotifier(name: String?): Boolean {
        logMessage(TAG, "FirebaseInAppMessaging: callNotifier() called...")
        return callForeground(name!!)
    }

    lateinit var userSegments: List<String>

    fun setPrefAndSegments(segments: List<String>) {
        userSegments = segments
    }
    /**
     * Determines whether automatic data collection is enabled or not.
     *
     * @return true if auto initialization is required
     */
    /**
     * Enables, disables, or clears automatic data collection for Firebase In-App Messaging.
     *
     *
     * When enabled, generates a registration token on app startup if there is no valid one and
     * generates a new token when it is deleted (which prevents [ ][FirebaseInstallationsApi.delete] from stopping the periodic sending of data). This setting is
     * persisted across app restarts and overrides the setting specified in your manifest.
     *
     *
     * When null, the enablement of the auto-initialization depends on the manifest and then on the
     * global enablement setting in this order. If none of these settings are present then it is
     * enabled by default.
     *
     *
     * If you need to change the default, (for example, because you want to prompt the user before
     * generates/refreshes a registration token on app startup), add the following to your
     * application’s manifest:
     *
     * <pre>`<meta-data android:name="firebase_inapp_messaging_auto_init_enabled" android:value="false" />
    `</pre> *
     *
     *
     * Note, this will require you to manually initialize Firebase In-App Messaging, via:
     *
     * <pre>`FirebaseInAppMessaging.getInstance().setAutomaticDataCollectionEnabled(true)`</pre>
     *
     *
     * Manual initialization will also be required in order to clear these settings and fall back
     * on other settings, via:
     *
     * <pre>`FirebaseInAppMessaging.getInstance().setAutomaticDataCollectionEnabled(null)`</pre>
     *
     * @param isAutomaticCollectionEnabled Whether isEnabled
     */
    var isAutomaticDataCollectionEnabled: Boolean?
        get() = dataCollectionHelper.isAutomaticDataCollectionEnabled
        set(isAutomaticCollectionEnabled) {
            dataCollectionHelper.setAutomaticDataCollectionEnabled(isAutomaticCollectionEnabled)
        }

    /**
     * Enables, disables, or clears automatic data collection for Firebase In-App Messaging.
     *
     *
     * When enabled, generates a registration token on app startup if there is no valid one and
     * generates a new token when it is deleted (which prevents [ ][FirebaseInstallationsApi.delete] from stopping the periodic sending of data). This setting is
     * persisted across app restarts and overrides the setting specified in your manifest.
     *
     *
     * By default, auto-initialization is enabled. If you need to change the default, (for example,
     * because you want to prompt the user before generates/refreshes a registration token on app
     * startup), add to your application’s manifest:
     *
     * <pre>`<meta-data android:name="firebase_inapp_messaging_auto_init_enabled" android:value="false" />
    `</pre> *
     *
     *
     * Note, this will require you to manually initialize Firebase In-App Messaging, via:
     *
     * <pre>`FirebaseInAppMessaging.getInstance().setAutomaticDataCollectionEnabled(true)`</pre>
     *
     * @param isAutomaticCollectionEnabled Whether isEnabled
     */
    fun setAutomaticDataCollectionEnabled(isAutomaticCollectionEnabled: Boolean) {
        dataCollectionHelper.setAutomaticDataCollectionEnabled(isAutomaticCollectionEnabled)
    }

    /**
     * Enables or disables suppression of Firebase In App Messaging messages.
     *
     *
     * When enabled, no in app messages will be rendered until either you either disable
     * suppression, or the app restarts, as this state is not preserved over app restarts.
     *
     *
     * By default, messages are not suppressed.
     *
     * @param areMessagesSuppressed Whether messages should be suppressed
     */
    fun setMessagesSuppressed(areMessagesSuppressed: Boolean) {
        this.areMessagesSuppressed = areMessagesSuppressed
    }

    /**
     * Determines whether messages are suppressed or not. This is honored by the UI sdk, which handles
     * rendering the in app message.
     *
     * @return true if messages should be suppressed
     */
    fun areMessagesSuppressed(): Boolean {
        return areMessagesSuppressed
    }

    /**
     * Sets message display component for FIAM SDK. This is the method used by both the default FIAM
     * display SDK or any app wanting to customize the message display.
     */
    fun setMessageDisplayComponent(messageDisplay: FirebaseInAppMessagingDisplay) {
        Logging.logi("Setting display event component")
        fiamDisplay = messageDisplay
    }

    /**
     * Unregisters a fiamDisplay to in app message display events.
     *
     * @hide
     */
    fun clearDisplayListener() {
        Logging.logi("Removing display event component")
        fiamDisplay = null
    }

    /**
     * Registers an impression listener with FIAM, which will be notified on every FIAM impression.
     *
     * @param impressionListener
     */
    fun addImpressionListener(
        impressionListener: FirebaseInAppMessagingImpressionListener
    ) {
        developerListenerManager.addImpressionListener(impressionListener)
    }

    /**
     * Registers a click listener with FIAM, which will be notified on every FIAM click.
     *
     * @param clickListener
     */
    fun addClickListener(clickListener: FirebaseInAppMessagingClickListener) {
        developerListenerManager.addClickListener(clickListener)
    }

    /**
     * Registers a dismiss listener with FIAM, which will be notified on every FIAM dismiss.
     *
     * @param dismissListener
     */
    fun addDismissListener(dismissListener: FirebaseInAppMessagingDismissListener) {
        developerListenerManager.addDismissListener(dismissListener)
    }

    /**
     * Registers a display error listener with FIAM, which will be notified on every FIAM display
     * error.
     *
     * @param displayErrorListener
     */
    fun addDisplayErrorListener(
        displayErrorListener: FirebaseInAppMessagingDisplayErrorListener
    ) {
        developerListenerManager.addDisplayErrorListener(displayErrorListener)
    }

    /**
     * Registers an impression listener with FIAM, which will be notified on every FIAM impression,
     * and triggered on the provided executor.
     *
     * @param impressionListener
     * @param executor
     */
    fun addImpressionListener(
        impressionListener: FirebaseInAppMessagingImpressionListener,
        executor: Executor
    ) {
        developerListenerManager.addImpressionListener(impressionListener, executor)
    }

    /**
     * Registers a click listener with FIAM, which will be notified on every FIAM click, and triggered
     * on the provided executor.
     *
     * @param clickListener
     * @param executor
     */
    fun addClickListener(
        clickListener: FirebaseInAppMessagingClickListener, executor: Executor
    ) {
        developerListenerManager.addClickListener(clickListener, executor)
    }

    /**
     * Registers a dismiss listener with FIAM, which will be notified on every FIAM dismiss, and
     * triggered on the provided executor.
     *
     * @param dismissListener
     * @param executor
     */
    fun addDismissListener(
        dismissListener: FirebaseInAppMessagingDismissListener, executor: Executor
    ) {
        developerListenerManager.addDismissListener(dismissListener, executor)
    }

    /**
     * Registers a display error listener with FIAM, which will be notified on every FIAM display
     * error, and triggered on the provided executor.
     *
     * @param displayErrorListener
     * @param executor
     */
    fun addDisplayErrorListener(
        displayErrorListener: FirebaseInAppMessagingDisplayErrorListener,
        executor: Executor
    ) {
        developerListenerManager.addDisplayErrorListener(displayErrorListener, executor)
    }

    /**
     * Unregisters an impression listener.
     *
     * @param impressionListener
     */
    fun removeImpressionListener(
        impressionListener: FirebaseInAppMessagingImpressionListener
    ) {
        developerListenerManager.removeImpressionListener(impressionListener)
    }

    /**
     * Unregisters a click listener.
     *
     * @param clickListener
     */
    fun removeClickListener(clickListener: FirebaseInAppMessagingClickListener) {
        developerListenerManager.removeClickListener(clickListener)
    }

    /**
     * Unregisters a display error listener.
     *
     * @param displayErrorListener
     */
    fun removeDisplayErrorListener(
        displayErrorListener: FirebaseInAppMessagingDisplayErrorListener
    ) {
        developerListenerManager.removeDisplayErrorListener(displayErrorListener)
    }

    /**
     * Removes all registered listeners.
     *
     * @hide
     */
    fun removeAllListeners() {
        developerListenerManager.removeAllListeners()
    }

    /**
     * Programmatically triggers a contextual trigger. This will display any eligible in-app messages
     * that are triggered by this event.
     *
     * @param eventName
     */
    fun triggerEvent(eventName: String) {
        programaticContextualTriggers.triggerEvent(eventName)
    }

    private fun triggerInAppMessage(inAppMessage: TriggeredInAppMessage) {
        logMessage(
            TAG,
            "triggerInAppMessage() called, inAppMessage.inAppMessage: ${inAppMessage.inAppMessage}, inAppMessage.triggeringEvent: ${inAppMessage.triggeringEvent}..."
        )
        fiamDisplay?.displayMessage(
            inAppMessage.inAppMessage,
            displayCallbacksFactory.generateDisplayCallback(
                inAppMessage.inAppMessage, inAppMessage.triggeringEvent
            )
        )
    }

    init {
        firebaseInstallations
            .id
            .addOnSuccessListener { id: String ->
                Logging.logi(
                    "Starting InAppMessaging runtime with Installation ID $id"
                )
            }
    }


    private fun triggerInAppMessage(
        minArticleViews: Long,
        inAppMessages: List<TriggeredInAppMessage>
    ) {
        logMessage(TAG, "triggerInAppMessage() called...}")
        logMessage(TAG, "triggerInAppMessage() called...inAppMessages.size:${inAppMessages.size}")
        val segmentsList = inAppMessages.filterList {
            (inAppMessage?.data?.containsKey(SEGMENT) == true
                    && userSegments.contains(inAppMessage.data?.get(SEGMENT)))
                    || inAppMessage?.data?.containsKey(SEGMENT) == false
        }
        logMessage(TAG, "triggerInAppMessage() called, segmentsList.size:${segmentsList.size} ...")

        val minArticleList = segmentsList.filterList {
            inAppMessage?.data?.containsKey(MIN_ARTICLE_VIEWS) == true
        }.filter {
            (it.inAppMessage.data?.get(MIN_ARTICLE_VIEWS)?.toInt()
                ?: 0) >= minArticleViews
        }
        logMessage(
            TAG,
            "triggerInAppMessage() called, minArticleList.size:${minArticleList.size} ..."
        )

        val noMinArticleList = segmentsList.filterList {
            inAppMessage?.data?.containsKey(MIN_ARTICLE_VIEWS) == false
        }

        logMessage(
            TAG,
            "triggerInAppMessage() called, noMinArticleList.size:${noMinArticleList.size} ..."
        )

        val mergedList = mergeList(minArticleList, noMinArticleList)

        mergedList.firstOrNull()?.let {
            triggerInAppMessage(it)
        } ?: kotlin.run {
            logMessage(TAG, "triggerInAppMessage() called, mergedList is empty/null ...")
        }
    }

    private fun <T> mergeList(vararg lists: List<T>): List<T> {
        val localList = mutableSetOf<T>()
        for (list in lists) {
            localList += list
        }
        return localList.toMutableList()
    }

    companion object {
        /**
         * Gets FirebaseInAppMessaging instance using the firebase app returned by [ ][FirebaseApp.getInstance]
         */
        val instance: FirebaseInAppMessaging
            get() = FirebaseApp.getInstance().get(
                FirebaseInAppMessaging::class.java
            )
        private const val SEGMENT = "segment"
        private const val MIN_ARTICLE_VIEWS = "min_article_views"
    }
}