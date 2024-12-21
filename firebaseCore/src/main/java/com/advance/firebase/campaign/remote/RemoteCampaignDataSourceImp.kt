package com.advance.firebase.campaign.remote

import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.request.*
import javax.inject.Inject

class RemoteCampaignDataSourceImp @Inject constructor(private val client: HttpClient) :
    RemoteCampaignDataSource {
    override suspend fun getCampaigns(url: String): String {
        return client.get(url) {
            headers.append("No-Authentication", "true")
        }.body()
    }
}
