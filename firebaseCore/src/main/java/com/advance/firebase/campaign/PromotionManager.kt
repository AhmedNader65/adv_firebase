package com.advance.firebase.campaign

import android.content.Context
import com.advance.domain.firebase.prefs.Prefs
import com.advance.firebase.Constant
import com.advance.firebase.campaign.events.CampaignNetworkEvent
import com.advance.firebase.core.commands.CampaignDaysCounterCommand
import com.advance.firebase.core.commands.CampaignStatusCommand
import com.advance.firebase.core.remoteConfig.AdvanceRemoteConfig
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import org.joda.time.Days
import org.joda.time.LocalDate
import org.joda.time.format.ISODateTimeFormat
import javax.inject.Inject

class PromotionManager @Inject constructor(
    private val campaignDaysCounterCommand: CampaignDaysCounterCommand,
    private val campaignStatusCommand: CampaignStatusCommand,
    private val prefs: Prefs,
    private val context: Context,
    private val scope: CoroutineScope,
    private val remoteConfig: AdvanceRemoteConfig
) {

    //    [
//    {
//        "id": "free_trial",
//        "minFirstInstallVersion": "4.1.14",
//        "numberOfFreeDays": 7,
//        "freeDayCampaignId": "ed92b812-e4c2-4cfb-8291-6be929feb1ae",
//        "offerId": "introyearly",
//        "offerAvailabilityDays": 1,
//        "offerCampaignId": "516faf07-48b0-4152-8bb5-0c32da0b26b6"
//    }
//    ]
    //    00005538286b554e
//       ->  campaigns
//           ->516faf07-48b0-4152-8bb5-0c32da0b26b6
//              -> complete:2
//                 dateOfFirstActivate:"2022-07-30"
    private val mutableSharedFlow = MutableSharedFlow<CampaignNetworkEvent>()
    val event: SharedFlow<CampaignNetworkEvent> = mutableSharedFlow

    private fun getDatabaseReference() = FirebaseDatabase.getInstance().reference
        .child(Constant.REMOTE_CONFIG_MAIN_NODE)
        .child(Constant.deviceId(context))
        .child(Constant.TRIAL_CAMPAIGNS)
        .child(remoteConfig.getOfferCampaignId())

    fun checkProm() {
        getDatabaseReference().addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        val completeStatus =
                            dataSnapshot.child("complete").getValue(Int::class.java)
                        val dateOfFirstActivate =
                            dataSnapshot.child("dateOfFirstActivate").getValue(String::class.java)

                        when (completeStatus) {
                            1 -> {
                                if (dateOfFirstActivate != null) {
                                    val start: LocalDate =
                                        LocalDate.parse(dateOfFirstActivate)
                                    prefs.activePromotionDate =
                                        ISODateTimeFormat.date().print(start)
                                    prefs.activePromotionId = remoteConfig.getFreeTrialOffering()
                                    broadcastEvent(CampaignNetworkEvent.CampaignDatesUpdatd)
                                } else {
                                }
                            }

                            else -> {
                                // completed
                            }
                        }
                    } else {
                        newCampien()
                        broadcastEvent(CampaignNetworkEvent.CampaignDatesUpdatd)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                }
            })
    }

    private fun newCampien() {
        try {
            prefs.activePromotionDate =
                ISODateTimeFormat.date().print(LocalDate.now())
            prefs.activePromotionId = remoteConfig.getFreeTrialOffering()
            campaignDaysCounterCommand.execute()
            campaignStatusCommand.execute(Constant.TRIAL_IN_PROGRESS_STATUS)
        } catch (e: NoSuchElementException) {
        }
    }

    private fun shouldActivatePromotion(isLoggedIn: Boolean): Boolean {
        // Use remote config and auth status to determine if 7 day free trial should be activated
        return if (isLoggedIn) {
            remoteConfig.getEnableFreeTrialAuthenticated()
        } else {
            remoteConfig.getEnableFreeTrial()
        }
    }

    fun startOfTheTrial(isLoggedIn: Boolean) {
        if (!shouldActivatePromotion(isLoggedIn)) return
        val start: LocalDate =
            LocalDate.parse(prefs.activePromotionDate)
        val end: LocalDate = LocalDate.now()
        val days = Days.daysBetween(end, start).days
        if (days > 0) {
            broadcastEvent(
                CampaignNetworkEvent.TrialEventReminder
            )
        } else {
            // valid for 2 days
            broadcastEvent(
                CampaignNetworkEvent.TrialEventBlocker
            )
        }
    }

    fun markAsCompleteStatus() {
        campaignStatusCommand.execute(Constant.TRIAL_COMPLETE_STATUS)
    }

    private fun broadcastEvent(localeEvent: CampaignNetworkEvent) {
        scope.launch {
            mutableSharedFlow.emit(localeEvent)
        }
    }
}
