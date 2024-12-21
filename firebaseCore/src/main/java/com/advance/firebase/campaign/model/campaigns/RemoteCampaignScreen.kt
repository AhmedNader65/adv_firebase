package com.advance.firebase.campaign.model.campaigns

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteCampaignScreen(

  @SerialName("primary_button")
  val remotePrimaryButton: RemotePrimaryButton? = null,
  @SerialName("primary_image_url")
  val primaryImageUrl: String? = null,
  @SerialName("primary_text")
  val primaryText: String? = null,
  @SerialName("secondary_image_url")
  val secondaryImageUrl: String? = null,
  @SerialName("secondary_text")
  val secondaryText: String? = null
)
