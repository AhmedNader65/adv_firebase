package com.advance.firebase.campaign.model.campaigns

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TroScreen(
  @SerialName("primary_button")
  val primaryButton: AryButton,

  @SerialName("primary_image_url")
  val primaryImageURL: String,

  @SerialName("primary_text")
  val primaryText: String,

  @SerialName("secondary_image_url")
  val secondaryImageURL: String? = null,

  @SerialName("secondary_text")
  val secondaryText: String
)
