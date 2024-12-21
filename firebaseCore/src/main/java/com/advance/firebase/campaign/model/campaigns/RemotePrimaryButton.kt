package com.advance.firebase.campaign.model.campaigns

import kotlinx.serialization.Serializable

@Serializable
data class RemotePrimaryButton(
  val title: String,
  val url: String? = null
)
