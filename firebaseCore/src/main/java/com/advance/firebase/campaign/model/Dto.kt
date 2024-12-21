package com.advance.firebase.campaign.model

import com.advance.cache.database.entities.campaign.BulletEntity
import com.advance.cache.database.entities.campaign.CampaignEntity
import com.advance.cache.database.entities.campaign.CampaignScreenEntity
import com.advance.cache.database.entities.campaign.PrimaryButtonEntity
import com.advance.domain.model.firebase.Bullet
import com.advance.domain.model.firebase.Campaign
import com.advance.domain.model.firebase.CampaignScreen
import com.advance.domain.model.firebase.PrimaryButton

fun Campaign.toEntity(): CampaignEntity {
  return CampaignEntity(
    type,
    id,
      name,
    primaryImageUrl,
    secondaryImageUrl,
    primaryButtonTitle,
    secondaryButtonTitle,
    primaryText,
    secondaryText,
    bullets?.map { it.toEntity() },
    primaryButtonUrl,
    secondaryButtonUrl,
    version,
    reportingId,
    CampaignScreenEntity(
      PrimaryButtonEntity(introScreen?.primaryButton?.title,
        introScreen?.primaryButton?.url),
      introScreen?.primaryImage_url, introScreen?.primaryText, introScreen?.secondaryImageUrl, introScreen?.secondaryText),
    CampaignScreenEntity(
      PrimaryButtonEntity(outroScreen?.primaryButton?.title,
        outroScreen?.primaryButton?.url),
      outroScreen?.primaryImage_url, outroScreen?.primaryText, outroScreen?.secondaryImageUrl, outroScreen?.secondaryText),
    surveyId,
    surveyVersion
  )
}

fun Bullet.toEntity(): BulletEntity {
  return BulletEntity(icon, text)
}

fun BulletEntity.toEntity(): Bullet {
  return Bullet(icon, text)
}

fun CampaignEntity.toCampaign(): Campaign {
  return Campaign(
    type ?: "",
    id,
    name!!,
    surveyId,
    surveyVersion,
    primaryImageUrl,
    secondaryImageUrl,
    primaryButtonTitle,
    secondaryButtonTitle,
    primaryText,
    secondaryText,
    bullets?.map { it.toEntity() },
    primaryButtonUrl,
    secondaryButtonUrl,
    version,
    reportingId,
    CampaignScreen(
      PrimaryButton(introScreen?.primaryButton?.title,
        introScreen?.primaryButton?.url),
      introScreen?.primaryImage_url, introScreen?.primaryText, introScreen?.secondaryImageUrl, introScreen?.secondaryText),
    CampaignScreen(
      PrimaryButton(outroScreen?.primaryButton?.title,
        outroScreen?.primaryButton?.url),
      outroScreen?.primaryImage_url, outroScreen?.primaryText, outroScreen?.secondaryImageUrl, outroScreen?.secondaryText)

  )
}
