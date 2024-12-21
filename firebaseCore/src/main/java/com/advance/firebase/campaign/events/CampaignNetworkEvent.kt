package com.advance.firebase.campaign.events

import com.advance.domain.model.firebase.Campaign
import com.advance.domain.model.firebase.SurveyData
import com.advance.domain.network.events.NetworkEvent
import com.google.firebase.inappmessaging.FirebaseInAppMessagingDisplayCallbacks
import com.google.firebase.inappmessaging.model.InAppMessage

sealed class CampaignNetworkEvent : NetworkEvent {

    data class NotificationEvent(
        val callbacks: FirebaseInAppMessagingDisplayCallbacks?,
        val notificationCampaign: Campaign
    ) : CampaignNetworkEvent()

    data class OpenSubscriptionEvent(
        val trialCampaign: Campaign
    ) : CampaignNetworkEvent()

    data class TrialEvent(
        val trialCampaign: Campaign
    ) : CampaignNetworkEvent()

    data class CampaignEvent(
        val campaign: List<Campaign>
    ) : CampaignNetworkEvent()

    object TrialEventReminder : CampaignNetworkEvent()

    object TrialEventBlocker : CampaignNetworkEvent()

    data class StartSurveyEvent(
        val survey: SurveyData?,
        val callbacks: FirebaseInAppMessagingDisplayCallbacks?,
    ) : CampaignNetworkEvent()

    data class ShowMessageEvent(
        val inAppMessage: InAppMessage?,
        val callbacks: FirebaseInAppMessagingDisplayCallbacks?,
    ) : CampaignNetworkEvent()

    data class StartIntroSurveyEvent(
        val campaign: Campaign?,
        val callbacks: FirebaseInAppMessagingDisplayCallbacks?,
    ) : CampaignNetworkEvent()

    object CampaignDatesUpdatd : CampaignNetworkEvent()
}
