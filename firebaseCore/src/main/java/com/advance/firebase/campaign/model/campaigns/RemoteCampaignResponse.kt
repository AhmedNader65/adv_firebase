package com.advance.firebase.campaign.model.campaigns

import kotlinx.serialization.Serializable

@Serializable
data class RemoteCampaignResponse(

  val data: Map<String, RemoteCampaignItem>
)
