package com.advance.firebase.campaign.model.campaigns

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject
import javax.inject.Inject

class CampaignIdAdapter @Inject constructor() {

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    explicitNulls = false }

    var remoteCampaignResponse: RemoteCampaignResponse? = null

    fun fromJson(reader: String): RemoteCampaignResponse? {
        val input = json.parseToJsonElement(reader) as JsonObject
        val data = mutableMapOf<String, RemoteCampaignItem>()

        input["campaigns"]?.let { value ->
                value.jsonObject.forEach { (campaignKey, campaignValue) ->
                    val campaign = json.decodeFromJsonElement<RemoteCampaignItem>(campaignValue)
                    data[campaignKey] = campaign
                }
        }

        remoteCampaignResponse = RemoteCampaignResponse(data)
        return remoteCampaignResponse
    }

    fun toJson(value: RemoteCampaignResponse?): String {
        return json.encodeToString(value)
    }
}
