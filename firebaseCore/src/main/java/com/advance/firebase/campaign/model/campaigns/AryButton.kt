package com.advance.firebase.campaign.model.campaigns

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AryButton(
  @SerialName("title")
  val title: String,
  @SerialName("url")
  val url: String? = null
)
