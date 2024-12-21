package com.advance.firebase.campaign.model.campaigns

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteBullet(
  @SerialName("icon")
  val icon: Icon,
  @SerialName("text")
  val text: String
)
