package com.advance.firebase.campaign

import android.content.Context
import com.advance.cache.datasource.compains.LocalCampaignDataSource
import com.advance.domain.affiliateinfo.AffiliateInfo
import com.advance.domain.firebase.prefs.Prefs
import com.advance.domain.model.firebase.AdvanceSegment
import com.advance.domain.model.firebase.Bullet
import com.advance.domain.model.firebase.Campaign
import com.advance.domain.model.firebase.CampaignScreen
import com.advance.domain.model.firebase.PrimaryButton
import com.advance.domain.model.firebase.SurveyData
import com.advance.domain.network.events.NetworkEvent
import com.advance.firebase.campaign.events.CampaignNetworkEvent
import com.advance.firebase.campaign.model.campaigns.CampaignIdAdapter
import com.advance.firebase.campaign.model.campaigns.RemoteBullet
import com.advance.firebase.campaign.model.campaigns.RemoteCampaignScreen
import com.advance.firebase.campaign.model.campaigns.Type
import com.advance.firebase.campaign.model.toCampaign
import com.advance.firebase.campaign.model.toEntity
import com.advance.firebase.campaign.remote.RemoteCampaignDataSource
import com.advance.firebase.core.inapp.FirebaseInAppMessagingImpl
import com.advance.utils.NetworkUtils
import com.google.firebase.inappmessaging.FirebaseInAppMessaging
import com.google.firebase.inappmessaging.FirebaseInAppMessagingDisplayCallbacks
import com.google.firebase.inappmessaging.model.MessageType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class CampaignRepository @Inject constructor(
    private val localCampaignDataSource: LocalCampaignDataSource,
    private val remoteCampaignDataSource: RemoteCampaignDataSource,
    private val affiliateInfo: AffiliateInfo,
    private val networkUtils: NetworkUtils,
    private val prefs: Prefs,
    private val scope: CoroutineScope,
) {

    private val mutableSharedFlow = MutableSharedFlow<NetworkEvent>(replay = 0)
    val event: SharedFlow<NetworkEvent> = mutableSharedFlow.asSharedFlow()

    fun initInAppMessaging(context: Context, list: List<AdvanceSegment>) {
        val messaging =
            FirebaseInAppMessagingImpl(
                prefs,
                context,
                list
            ) { id, versionId, campaignName, isCampaign, inAppMessage, messageType, callback ->

                if (messageType == MessageType.UNSUPPORTED) {
                    if (isCampaign) {
                        processCampaign(id, callback)
                    } else {
                        broadcastEvent(
                            CampaignNetworkEvent.StartSurveyEvent(
                                SurveyData(
                                    id,
                                    versionId,
                                    campaignName
                                ),
                                callback
                            )
                        )
                    }
                } else {
                    // Gz:Display the message

                    broadcastEvent(
                        CampaignNetworkEvent.ShowMessageEvent(
                            inAppMessage,
                            callback
                        )
                    )
                }
            }
        FirebaseInAppMessaging.instance.apply {
            logMessage("CampaignRepository", "FirebaseInAppMessaging.instance called...")
            setMessageDisplayComponent(messaging)
            runAdvanceFirebaseCheck()
            logMessage("CampaignRepository", "FirebaseInAppMessaging.instance end of method...")
            setPrefAndSegments(prefs, list.map { it.name })
        }
    }

    fun callNotifier(name: String): Boolean {
        if (networkUtils.isNetworkConnected) {
            return FirebaseInAppMessaging.instance.callNotifier(name)
        }
        return false
    }

    fun processCampaign(
        campaignId: String? = null,
        callbacks: FirebaseInAppMessagingDisplayCallbacks? = null
    ) {
        scope.launch {
            fetchCampaigns(campaignId, callbacks)
        }
    }

    private suspend fun fetchCampaigns(
        campaignId: String? = null,
        callbacks: FirebaseInAppMessagingDisplayCallbacks? = null
    ) {
        try {
            val response = remoteCampaignDataSource.getCampaigns(affiliateInfo.compainUrl)
            exportCampaign(response, campaignId, callbacks)
        } catch (throwable: Throwable) {
            Timber.d("fetchCampaigns error ${throwable.message}")
        }
    }

    private fun exportCampaign(
        responseBody: String,
        campaignId: String? = null,
        callbacks: FirebaseInAppMessagingDisplayCallbacks? = null
    ) {
        val data = CampaignIdAdapter().fromJson(responseBody)?.data?.map {
            Campaign(
                it.value.type?.name ?: "",
                it.key,
                it.value.campaign?.name!!,
                it.value.surveyId,
                it.value.surveyVersion,
                it.value.primaryImageURL,
                it.value.secondaryImageURL,
                it.value.primaryButton?.title,
                it.value.secondaryButton?.title,
                it.value.primaryText,
                it.value.secondaryText,
                it.value.bullets?.map { it.toBullet() },
                it.value.primaryButton?.url,
                it.value.secondaryButton?.url,
                it.value.campaign?.version,
                it.value.campaign?.reportingID ?: "",
                it.value.introScreenRemote?.let { it.toScreen() },
                it.value.outroScreenRemote?.let { it.toScreen() },
            )
        }.also {
            scope.launch {
                localCampaignDataSource.insert(it?.map { it.toEntity() } ?: listOf())
            }
        }

        if (campaignId == null) {
            data?.let {
                broadcastEvent(CampaignNetworkEvent.CampaignEvent(it))
            }
        } else {

            val campaign = data?.firstOrNull { it.id == campaignId }
            campaign?.type?.let { Timber.tag("campaignType ").i(it) }
            when (campaign?.type) {
                Type.Dialog4.name -> {
                    broadcastEvent(
                        CampaignNetworkEvent.NotificationEvent(
                            callbacks,
                            campaign
                        )
                    )
                }

                Type.Subscription.name -> {
                    if (callbacks == null) {
                        broadcastEvent(
                            CampaignNetworkEvent.OpenSubscriptionEvent(
                                campaign
                            )
                        )
                    }

                    Timber.tag("Subscription ").i("it")
                }

                Type.Survey.name -> {
                    broadcastEvent(
                        CampaignNetworkEvent.StartIntroSurveyEvent(
                            campaign,
                            callbacks
                        )
                    )
                }

                else -> {}
            }
        }
    }

    suspend fun getTrialCampaign(id: String) = channelFlow {
        withContext(Dispatchers.IO) {
            localCampaignDataSource.getBy(id)?.run {
                send(toCampaign())
            }
        }
    }

    private fun broadcastEvent(localeEvent: NetworkEvent) {
        scope.launch {
            Timber.d("***broadcastEvent $localeEvent")
            mutableSharedFlow.emit(localeEvent)
        }
    }
}

enum class PAGES(name: String) {
    TOP_STORIES("TOP_STORIES"),
    SEARCH("SEARCH"),
    FEED("FEED"),
    STORY_DETAILS("STORY_DETAILS"),
    READ_IT_LATER("READ_IT_LATER"),
    BACKGROUND("BACKROUND"),
}

fun RemoteBullet.toBullet(): Bullet {
    return Bullet(icon.name, text)
}

fun RemoteCampaignScreen.toScreen(): CampaignScreen {
    return CampaignScreen(
        PrimaryButton(remotePrimaryButton?.title, remotePrimaryButton?.url),
        primaryImageUrl,
        primaryText,
        secondaryImageUrl,
        secondaryText
    )
}
