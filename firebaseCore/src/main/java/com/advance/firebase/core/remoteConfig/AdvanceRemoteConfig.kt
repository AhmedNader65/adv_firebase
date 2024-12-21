package com.advance.firebase.core.remoteConfig

import com.advance.domain.BuildConfig
import com.advance.domain.firebase.RemoteConfigService
import com.advance.firebase.Constant
import com.advance.firebase.Constant.ENABLE_AUDIO_SUMMARY
import com.advance.firebase.Constant.ENABLE_PARSELY_ANALYTICS
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ConfigUpdate
import com.google.firebase.remoteconfig.ConfigUpdateListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import kotlinx.serialization.json.Json
import timber.log.Timber
import javax.inject.Singleton

@Singleton
class AdvanceRemoteConfig : RemoteConfigService {
    private val json = Json { ignoreUnknownKeys = true }

    fun start() {
        Firebase.remoteConfig.apply {
            setConfigSettingsAsync(remoteConfigSettings {
                minimumFetchIntervalInSeconds = if (BuildConfig.DEBUG) {
                    0 // Kept 0 for quick debug
                } else {
                    0
                }
            })
            setDefaultsAsync(DEFAULTS)
            fetchAndActivate().addOnCompleteListener {
            }
            realtimeUpdate()
        }
    }

    private fun realtimeUpdate() {
        Firebase.remoteConfig.addOnConfigUpdateListener(object : ConfigUpdateListener {
            override fun onUpdate(configUpdate: ConfigUpdate) {
                Timber.tag("AdvanceRemoteConfig : realtimeUpdate() ")
                    .w("Updated keys: " + configUpdate.updatedKeys)
                Firebase.remoteConfig.activate()
            }

            override fun onError(error: FirebaseRemoteConfigException) {
                Timber.tag("AdvanceRemoteConfig : realtimeUpdate() ")
                    .w("Config update error with code: " + error.code)
            }
        })
    }

    fun listAllConfig(): String =
        Firebase.remoteConfig.all.map { (key, value) ->
            "$key equals ${value.asString()} \n\n\n"
        }.joinToString(separator = ", ")

    override fun disablePiano(): Boolean =
        Firebase.remoteConfig.getBoolean(Constant.DISABLE_PIANO)

    override fun interestTrackingDays() =
        Firebase.remoteConfig.getLong(Constant.INTEREST_TRACKING_DAYS)

    override fun minimumInterestScore() =
        Firebase.remoteConfig.getLong(Constant.MINIMUM_INTEREST_SCORE)

    override fun maximumPromptIntervalHours() =
        Firebase.remoteConfig.getLong(Constant.MAXIMUM_PROMPT_INTERVAL_HOURS)

    override fun minimumPromptIntervalHours() =
        Firebase.remoteConfig.getLong(Constant.MINIMUM_PROMPT_INTERVAL_HOURS)

    override fun enableCustomAlerts() =
        Firebase.remoteConfig.getBoolean(Constant.ENABLE_CUSTOM_ALERTS)

    override fun enablePuzzles() =
        Firebase.remoteConfig.getBoolean(Constant.ENABLE_PUZZLES)

    override fun enableHomeFeedPref() =
        Firebase.remoteConfig.getBoolean(Constant.ENABLE_HOME_FEED_PREFERENCE)

    override fun defaultHomeFeedPref() =
        Firebase.remoteConfig.getString(Constant.DEFAULT_HOME_FEED_PREFERENCE)

    override fun enableAudioSummary() =
        Firebase.remoteConfig.getBoolean(Constant.ENABLE_AUDIO_SUMMARY)

    fun disablePianoPaywall(): Boolean =
        Firebase.remoteConfig.getBoolean(Constant.DISABLE_PIANOP_PAYWALL)

    fun getForceVersion(): String =
        Firebase.remoteConfig.getString(Constant.FORCE_UPGRADE_VERSION)

    fun getForceMessage(): String =
        Firebase.remoteConfig.getString(Constant.FORCE_UPGRADE_MESSAGE)

    fun getRecommendVersion(): String =
        Firebase.remoteConfig.getString(Constant.RECOMMEND_UPGRADE_VERSION)

    fun getRecommendMessage(): String =
        Firebase.remoteConfig.getString(Constant.RECOMMEND_UPGRADE_MESSAGE)

    fun getFirebaseProjectName(): String =
        Firebase.remoteConfig.getString(Constant.FIREBASE_PROJECT)

    override fun enableParselyAnalytics(): Boolean =
        Firebase.remoteConfig.getBoolean(Constant.ENABLE_PARSELY_ANALYTICS)

    override fun enableFirebaseAnalyticsScreenView(): Boolean =
        Firebase.remoteConfig.getBoolean(Constant.ENABLE_FIREBASE_ANALYTICS_SCREEN_VIEW)

    override fun enableFirebaseAnalyticsAdInsertion(): Boolean =
        Firebase.remoteConfig.getBoolean(Constant.ENABLE_FIREBASE_ANALYTICS_AD_INSERTION)

    override fun getSubscriptionOffering(): String {
        return Firebase.remoteConfig.getString(Constant.SUBSCRIPTION_OFFERING)
    }

    override fun getSubscriptionOfferingAuthenticated(): String {
        return Firebase.remoteConfig.getString(Constant.SUBSCRIPTION_OFFERING_AUTHENTICATED)
    }

    override fun getEnableFreeTrial(): Boolean {
        return Firebase.remoteConfig.getBoolean(Constant.ENABLE_FREE_TRIAL)
    }

    override fun getEnableFreeTrialAuthenticated(): Boolean {
        return Firebase.remoteConfig.getBoolean(Constant.ENABLE_FREE_TRIAL_AUTHENTICATED)
    }

    override fun getFreeTrialOffering(): String {
        return Firebase.remoteConfig.getString(Constant.FREE_TRIAL_OFFERING)
    }

    override fun getOfferCampaignId(): String {
        return Firebase.remoteConfig.getString(Constant.FREE_OFFER_CAMPAIGN_ID)
    }

    override fun getTermId(termId: String): String {
        val termsId = Firebase.remoteConfig.getString(Constant.PIANO_TERMS_ID_JSON)
        return json.decodeFromString<Map<String, String>>(termsId)[termId] ?: ""
    }

    override fun getTermsId(): List<String> {
        val termsId = Firebase.remoteConfig.getString(Constant.PIANO_TERMS_ID_LIST)
        return termsId.split(",")
    }

    companion object {
        private const val recommendMessage =
            "Your app should be upgraded to get the best experience. Tap the button below to open the App Store and then tap 'Upgrade'."
        private const val forceMessage =
            "Your app needs to be updated to continue working. Tap the button below to open the App Store and then tap 'Upgrade'."

        private val DEFAULTS: HashMap<String, Any> =
            hashMapOf(
                Constant.AUTH0_ENABLED to true,
                Constant.AUTH0_REQUIRED to true,
                Constant.AUTH0_DOMAIN to BuildConfig.AUTH0_DOMAIN,
                Constant.AUTH0_CLIENT_ID to BuildConfig.AUTH0_CLIENT_ID,
                Constant.DISABLE_PIANO to false,
                Constant.DISABLE_PIANOP_PAYWALL to false,
                Constant.FORCE_UPGRADE_VERSION to BuildConfig.BUILD_NUMBER,
                Constant.FORCE_UPGRADE_MESSAGE to forceMessage,
                Constant.RECOMMEND_UPGRADE_VERSION to BuildConfig.BUILD_NUMBER,
                Constant.RECOMMEND_UPGRADE_MESSAGE to recommendMessage,
                Constant.FIREBASE_PROJECT to "",
                Constant.INTEREST_TRACKING_DAYS to 10,
                Constant.MINIMUM_INTEREST_SCORE to .5,
                Constant.MAXIMUM_PROMPT_INTERVAL_HOURS to 168,
                Constant.MINIMUM_PROMPT_INTERVAL_HOURS to 24,
                Constant.ENABLE_CUSTOM_ALERTS to true,
                Constant.SUBSCRIPTION_OFFERING to "",
                Constant.SUBSCRIPTION_OFFERING_AUTHENTICATED to "",
                Constant.ENABLE_FREE_TRIAL to true,
                Constant.ENABLE_FREE_TRIAL_AUTHENTICATED to true,
                Constant.FREE_TRIAL_OFFERING to "",
                Constant.FREE_OFFER_CAMPAIGN_ID to "",
                Constant.PIANO_TERMS_ID_JSON to "",
                Constant.PIANO_TERMS_ID_LIST to "",
                ENABLE_PARSELY_ANALYTICS to true,
                ENABLE_AUDIO_SUMMARY to true

            )
    }
}
