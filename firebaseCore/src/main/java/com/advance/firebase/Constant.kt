package com.advance.firebase

import android.content.Context
import android.provider.Settings

object Constant {

    const val AUTH0_ENABLED = "auth0Enabled"
    const val AUTH0_REQUIRED = "auth0Required"

    const val AUTH0_DOMAIN = "auth0Domain"
    const val AUTH0_CLIENT_ID = "auth0ClientId"
    const val DISABLE_PIANO = "disablePiano"
    const val DISABLE_PIANOP_PAYWALL = "disablePianoPaywall"

    const val FORCE_UPGRADE_VERSION = "forceUpgradeVersion"
    const val FORCE_UPGRADE_MESSAGE = "forceUpgradeMessage"
    const val RECOMMEND_UPGRADE_VERSION = "recommendUpgradeVersion"
    const val RECOMMEND_UPGRADE_MESSAGE = "recommendUpgradeMessage"

    const val FIREBASE_PROJECT = "firebaseProject"

    const val ENABLE_PARSELY_ANALYTICS = "enableParselyAnalyticsAndroid"
    const val ENABLE_FIREBASE_ANALYTICS_SCREEN_VIEW = "enableFirebaseAnalyticsScreenView"

    const val ENABLE_FIREBASE_ANALYTICS_AD_INSERTION = "enableFirebaseAdInsertionAnalytics"

    fun deviceId(context: Context) = context.deviceId()

    const val TRIAL_INIT_STATUS = 0L
    const val TRIAL_IN_PROGRESS_STATUS = 1L
    const val TRIAL_COMPLETE_STATUS = 2L

    // trial main node
    const val REMOTE_CONFIG_BC_MAPPING = "profiles"
    const val REMOTE_CONFIG_MAIN_NODE = "devices"
    const val REMOTE_CONFIG_DATE_OF_FIRST_INSTALL = "dateOfFirstInstall"
    const val REMOTE_CONFIG_VERSION_OF_FIRST_INSTALL = "firstVersionInstalled"
    const val TRIAL_CAMPAIGNS = "campaigns"
    const val REMOTE_MUID = "mobile_user_identifier"

    const val TRIAL_CAMPAIGNS_DATE_OF_FIRST_ACTIVATE = "dateOfFirstActivate"
    const val TRIAL_CAMPAIGNS_COMPLETE = "complete"
    const val REMOTE_CONFIG_MIN_ARTICLE_VIEW = "minArticleViews"
    const val INTEREST_TRACKING_DAYS = "interestTrackingDays"

    const val MINIMUM_INTEREST_SCORE = "minimumInterestScore"
    const val MAXIMUM_PROMPT_INTERVAL_HOURS = "maximumPromptIntervalHours"
    const val MINIMUM_PROMPT_INTERVAL_HOURS = "minimumPromptIntervalHours"
    const val ENABLE_CUSTOM_ALERTS = "enableCustomAlerts"
    const val ENABLE_PUZZLES = "enablePuzzles"
    const val ENABLE_HOME_FEED_PREFERENCE = "enableHomeFeedPreference"
    const val DEFAULT_HOME_FEED_PREFERENCE = "defaultHomeFeedPreference"
    const val ENABLE_AUDIO_SUMMARY = "enableAudioSummaryPlayer"

    const val SUBSCRIPTION_OFFERING = "subscriptionOffering"
    const val SUBSCRIPTION_OFFERING_AUTHENTICATED = "subscriptionOfferingAuthenticated"
    const val ENABLE_FREE_TRIAL = "enableFreeTrial"
    const val ENABLE_FREE_TRIAL_AUTHENTICATED = "enableFreeTrialAuthenticated"
    const val FREE_TRIAL_OFFERING = "freeTrialOffering"
    const val FREE_OFFER_CAMPAIGN_ID = "OfferCampaignId"
    const val PIANO_TERMS_ID_JSON = "pianoTermIdsJson"
    const val PIANO_TERMS_ID_LIST = "pianoTermIds"
}
fun Context.deviceId() = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
