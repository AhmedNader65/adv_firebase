package com.advance.firebase.campaign

// class TrialCampaignManagement(
//  private val campaignDaysCounterCommand: CampaignDaysCounterCommand,
//  private val campaignStatusCommand: CampaignStatusCommand,
//  private val prefs: Prefs,
//  private val context: Context,
//  private val scope: CoroutineScope
// ) {
//  private val mutableSharedFlow = MutableSharedFlow<CampaignNetworkEvent>()
//  val event: SharedFlow<CampaignNetworkEvent> = mutableSharedFlow
//
//  fun checkActiveTrials(list: List<Promotions?>) {
//
//    if (list.isEmpty()) {
//        return
//    }
//
//    try {
//      Firebase.database.reference.child(REMOTE_CONFIG_MAIN_NODE)
//        .child(Constant.deviceId(context))
//        .child(TRIAL_CAMPAIGNS).get().addOnSuccessListener { it ->
//          if (it.exists()) {
//            val campaignMaps = (it.value as Map<*, *>)
//
//            // if there is no campaign, get first promation and save it
//            val filteredCampaignIds = list
//              .map { it?.offerCampaignId }
//              .map { id ->
//                campaignMaps.filter { campaign ->
//                  try {
//                    val campaignMap = campaign.value as Map<*, *>
//                    campaign.key == id &&
//                      campaignMap[TRIAL_CAMPAIGNS_COMPLETE] as Long? in listOf(
//                        TRIAL_INIT_STATUS,
//                        TRIAL_IN_PROGRESS_STATUS
//                      )
//                  } catch (e: ClassCastException) {
//                    false
//                  }
//                }
//              }
//
//            val filter = filteredCampaignIds.firstOrNull { campaignMap ->
//              campaignMap[TRIAL_CAMPAIGNS_COMPLETE] as Long? == TRIAL_IN_PROGRESS_STATUS
//            } ?: filteredCampaignIds.first()
//
//            filter.map { campaign ->
//              val promotion =
//                list
//                  .first { it?.offerCampaignId == campaign.key }
//              val campaignMap = campaign.value as Map<*, *>
//              val start: LocalDate =
//                LocalDate.parse(campaignMap[TRIAL_CAMPAIGNS_DATE_OF_FIRST_ACTIVATE] as String?)
//              val end: LocalDate = LocalDate.now()
//              val days = Days.daysBetween(start, end).days
//              TrialCampaign(
//                campaign.key as String, start, days,
//                promotion?.numberOfFreeDays?.minus(days) ?: 0,
//                promotion!!
//              )
//            }.filter { it.hasMoreDaysLeft >= 0 }
//              .maxByOrNull { it.numberOfDays }?.apply {
//                prefs.activePromotionDate = ISODateTimeFormat.date().print(date)
//                prefs.activePromotionId = id
//              }
//            broadcastEvent(CampaignNetworkEvent.CampaignDatesUpdatd)
//          } else {
//            broadcastEvent(CampaignNetworkEvent.CampaignDatesUpdatd)
//
//            try {
//              if (prefs.dateOfFirstInstall.daysLeft() == 0) {
//                val promotion = list.first()
//                val campaignId = promotion!!.offerCampaignId
//                prefs.activePromotionDate =
//                  ISODateTimeFormat.date().print(LocalDate.now())
//                prefs.activePromotionId = campaignId
//                campaignDaysCounterCommand.execute()
//                campaignStatusCommand.execute(TRIAL_IN_PROGRESS_STATUS)
//              }
//            } catch (e: NoSuchElementException) {
//            }
//          }
//        }
//    } catch (e: ClassCastException) {
//    } catch (e: Exception) {
//    }
//  }
//
//  fun startOfTheTrial(list: List<Promotions?>) {
//    val trialCampaign = getActivePromotion(list, prefs) ?: return
//    if (trialCampaign.hasMoreDaysLeft > 0) {
//      // show reminder?
//      broadcastEvent(
//        CampaignNetworkEvent.TrialEventReminder(
//          trialCampaign.hasMoreDaysLeft,
//          trialCampaign.promotions
//        )
//      )
//    } else {
//      // valid for 2 days
//      val offerAvailabilityDays = trialCampaign.promotions.offerAvailabilityDays
//      val fromStart = kotlin.math.abs(trialCampaign.hasMoreDaysLeft) - offerAvailabilityDays
//      if (fromStart <= offerAvailabilityDays) {
//        // block the user
//        broadcastEvent(CampaignNetworkEvent.TrialEventBlocker(trialCampaign.promotions))
//      } else {
//        markAsCompleteStatus()
//      }
//    }
//  }
//
//  fun markAsCompleteStatus() {
//    campaignStatusCommand.execute(TRIAL_COMPLETE_STATUS)
//  }
//
//  private fun broadcastEvent(localeEvent: CampaignNetworkEvent) {
//    scope.launch {
//      mutableSharedFlow.emit(localeEvent)
//    }
//  }
// }
