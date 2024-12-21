package com.advance.firebase.campaign.model.campaigns

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Campaign(

  @SerialName("id")
  val id: String? = null,

  @SerialName("impressions")
  val impressions: Long? = null,

  @SerialName("name")
  val name: String? = null,

  @SerialName("percentage")
  val percentage: Long? = null,

  @SerialName("prerequisites")
  val prerequisites: List<Prerequisite>? = null,

  @SerialName("reporting_id")
  val reportingID: String? = null,

  @SerialName("schema_version")
  val schemaVersion: Long? = null,

  @SerialName("type")
  val type: Type? = null,

  @SerialName("version")
  val version: String? = null

)
