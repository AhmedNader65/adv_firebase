package com.advance.firebase.campaign.remote

interface RemoteCampaignDataSource {
  suspend fun getCampaigns(url: String): String
}
