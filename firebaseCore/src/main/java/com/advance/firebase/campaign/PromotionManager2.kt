package com.advance.firebase.campaign

// class PromotionManager2(
//    context: Context,
//    private val remoteConfig: AdvanceRemoteConfig,
//    private val userService: UserService,
//    private val prefs: Prefs,
//    private val campaignStatusCommand: CampaignStatusCommand
//
// ) {
//
//    var promotions: List<Promotions?> = remoteConfig.getPromotions()
//    val activePromotion: ActivePromotion?
//        get() {
//            val id = prefs.activePromotionId
//            val promotion = findPromotion(id) ?: return null
//            val dateStr = prefs.activePromotionDate
//            val date = ISODateTimeFormat.date().print(dateStr)
//            return Promotions(promotion, date)
//        }
//    val availablePromotions: List<Promotions?>
//        get() {
//            val previousPromotionIds = previousPromotionIds()
//            return promotions.filter { promotion ->
//                if (previousPromotionIds.contains(promotion?.id)) {
//                    return@filter false
//                }
//                val firstVersionInstalled = prefs.firstVersionInstalled
//                promotion?.minFirstInstallVersion?.let {
//                    if (compareVersionStrings(firstVersionInstalled, it) < 0) {
//                        return@filter false
//                    }
//                }
//                true
//            }
//        }
//
//    fun shouldActivatePromotion(promotion: Promotions): Boolean {
//        // Use remote config and auth status to determine if 7 day free trial should be activated
//        if (promotion.numberOfFreeDays == 7) {
//            return if (userService.isLoggedIn()) {
//                remoteConfig.getEnableFreeTrialAuthenticated()
//            } else {
//                remoteConfig.getEnableFreeTrial()
//            }
//        }
//        return true
//    }
//
//    fun activatePromotion(
//        promotion: Promotions,
//        date: LocalDate = LocalDate.now()
//    ): ActivePromotion? {
//        if (!shouldActivatePromotion(promotion)) return null
//        val previousPromotionIds = previousPromotionIds().toMutableList()
//        if (previousPromotionIds.contains(promotion.id)) return null
//        previousPromotionIds.add(promotion.id)
//        val activePromotion = ActivePromotion(promotion, date)
// //        sharedPreferences.edit().putString("activePromotionId", promotion.id).apply()
// //        sharedPreferences.edit().putString("activePromotionStart", encodeISO8601Date(date)).apply()
//        return activePromotion
//    }
//
//    fun deactivatePromotion() {
//        campaignStatusCommand.execute(Constant.TRIAL_COMPLETE_STATUS)
//    }
//
//    private fun previousPromotionIds(): List<String> {
//        return
//    }
//
//    private fun findPromotion(id: String): Promotions? {
//        return promotions.firstOrNull { it?.id == id }
//    }
// }
//
// fun compareVersionStrings(version1: String, version2: String): Int {
//    return version1.compareTo(version2)
// }
//
// data class ActivePromotion(
//    val promotion: Promotions,
//    val activatedOn: LocalDate
// ) {
//    fun remainingDaysInFreeTrial(date: LocalDate = LocalDate.now()): Int {
//        return max(
//            0,
//            promotion.numberOfFreeDays - ChronoUnit.DAYS.between(activatedOn, date).toInt()
//        )
//    }
// }
//
