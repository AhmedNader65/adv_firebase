package com.advance.firebase.campaign.model.campaigns

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteCampaignItem(

  @SerialName("bullets")
  val bullets: List<RemoteBullet>? = null,

  @SerialName("campaign")
  val campaign: Campaign? = null,

  @SerialName("primary_button")
  val primaryButton: AryButton? = null,

  @SerialName("primary_image_url")
  val primaryImageURL: String? = null,

  @SerialName("primary_text")
  val primaryText: String? = null,

  @SerialName("secondary_button")
  val secondaryButton: AryButton? = null,

  @SerialName("secondary_image_url")
  val secondaryImageURL: String? = null,

  @SerialName("secondary_text")
  val secondaryText: String? = null,

  @SerialName("type")
  val type: Type? = null,

  @SerialName("survey_id")
  val surveyId: String? = null,

  @SerialName("survey_version")
  val surveyVersion: String? = null,

  @SerialName("intro_screen")
  val introScreenRemote: RemoteCampaignScreen? = null,

  @SerialName("outro_screen")
  val outroScreenRemote: RemoteCampaignScreen? = null

)
