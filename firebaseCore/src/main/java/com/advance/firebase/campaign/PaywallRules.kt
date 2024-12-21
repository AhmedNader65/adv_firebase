package com.advance.firebase.campaign

sealed class PaywallRules {
    object Allow : PaywallRules()
    object AllowAndInvokePiano : PaywallRules()
    data class AllowAndPresentFreeTrialUI(val campaignId: String, val remainingDays: Int) :
        PaywallRules()

    object BlockAndPresentPaywall : PaywallRules()
    data class BlockAndPresentOffer(val campaignId: String, val offerId: String) : PaywallRules()
}

// class PayWallRulesConfig(
//    private val promotionManager: PromotionManager2?,
//    private val remoteConfig: AdvanceRemoteConfig,
//    private val prefs: Prefs
// ) {
//    fun determineAccess(date: LocalDate = LocalDate.now()): PaywallRules {
//        // If promotions available, handle those first
//        promotionManager?.let { manager ->
//            // If not current activePromotion
//            if (manager.activePromotion == null && manager.availablePromotions.isNotEmpty()) {
//                manager.availablePromotions.firstOrNull()?.let { promotion ->
//                    if (manager.shouldActivatePromotion(promotion)) {
//                        manager.activatePromotion(promotion)
//                    }
//                }
//            }
//            manager.activePromotion?.let { activePromotion ->
//                val remainingDaysInFreeTrial = activePromotion.remainingDaysInFreeTrial(date)
//                if (remainingDaysInFreeTrial > 0) {
//                    activePromotion.promotion.freeDayCampaignId.let { campaignId ->
//                        return PaywallRules.AllowAndPresentFreeTrialUI(
//                            campaignId,
//                            remainingDaysInFreeTrial
//                        )
//                    }
//                }
//
//                if (remainingDaysInFreeTrial > 0) {
//                    return PaywallRules.Allow
//                } else {
//                    var offerId = activePromotion.promotion.offerId
//                    if (activePromotion.promotion.numberOfFreeDays == 7) {
//                        offerId = remoteConfig.getFreeTrialOffering().ifEmpty { offerId }
//                    }
//
//                    LocalDate.parse(prefs.activePromotionDate)?.let { offerStart ->
//                        val availabilityDays = activePromotion.promotion.offerAvailabilityDays
//                        val offerEnd = offerStart.plusDays(availabilityDays.toLong())
//                        if (offerEnd.isAfter(date)) {
//                            return PaywallRules.BlockAndPresentOffer(
//                                activePromotion.promotion.offerCampaignId,
//                                offerId
//                            )
//                        } else {
//                            manager.deactivatePromotion()
//                        }
//                    } ?: run {
//                        prefs.activePromotionDate =
//                            ISODateTimeFormat.date().print(LocalDate.now().toEpochDay())
//                        return PaywallRules.BlockAndPresentOffer(
//                            activePromotion.promotion.offerCampaignId,
//                            offerId
//                        )
//                    }
//                }
//            }
//        }
//        return PaywallRules.BlockAndPresentPaywall
//    }
//
// }
