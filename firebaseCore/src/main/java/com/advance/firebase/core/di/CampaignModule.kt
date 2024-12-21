package com.advance.firebase.core.di

import android.content.Context
import com.advance.cache.datasource.compains.LocalCampaignDataSource
import com.advance.domain.affiliateinfo.AffiliateInfo
import com.advance.domain.firebase.prefs.Prefs
import com.advance.firebase.campaign.CampaignRepository
import com.advance.firebase.campaign.remote.RemoteCampaignDataSource
import com.advance.firebase.core.commands.CampaignDaysCounterCommand
import com.advance.firebase.core.commands.CampaignStatusCommand
import com.advance.domain.threading.DispatcherProvider
import com.advance.firebase.campaign.PromotionManager
import com.advance.firebase.core.remoteConfig.AdvanceRemoteConfig
import com.advance.utils.NetworkUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CampaignModule {

    @Provides
    @Singleton
    fun provideCampaignRepository(
        localCampaignDataSource: LocalCampaignDataSource,
        remoteCampaignDataSource: RemoteCampaignDataSource,
        affiliateInfo: AffiliateInfo,
        networkUtils: NetworkUtils,
        prefs: Prefs,
        @CampaignCoroutineScope scope: CoroutineScope
    ): CampaignRepository {
        return CampaignRepository(
            localCampaignDataSource,
            remoteCampaignDataSource,
            affiliateInfo,
            networkUtils,
            prefs,
            scope
        )
    }

    @Provides
    @Singleton
    fun provideTrialCampaignManagement(
        campaignDaysCounterCommand: CampaignDaysCounterCommand,
        campaignStatusCommand: CampaignStatusCommand,
        @ApplicationContext context: Context,
        prefs: Prefs,
        @CampaignCoroutineScope scope: CoroutineScope,
        remoteConfig: AdvanceRemoteConfig,
    ): PromotionManager {
        return PromotionManager(
            campaignDaysCounterCommand,
            campaignStatusCommand,
            prefs,
            context,
            scope,
          //  userService,
            remoteConfig
        )
    }

    @CampaignCoroutineScope
    @Provides
    fun provideCampaignCoroutineScope(
        dispatcherProvider: DispatcherProvider
    ): CoroutineScope {
        return CoroutineScope(dispatcherProvider.io() + Job())
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CampaignCoroutineScope
