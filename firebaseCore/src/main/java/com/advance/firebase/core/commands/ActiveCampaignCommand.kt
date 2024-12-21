package com.advance.firebase.core.commands

import com.advance.command.chain.RequestChain
import com.advance.firebase.campaign.PromotionManager

class ActiveCampaignCommand(
  private val trialCampaignManagement: PromotionManager
) : RequestChain {

  override fun request(any: Any) {
    try {
      trialCampaignManagement.checkProm()
    } catch (e: ClassCastException) {
    }
  }
}
