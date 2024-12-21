package com.advance.firebase.core.di

import android.content.Context
import com.advance.domain.affiliateinfo.AffiliateInfo
import com.advance.firebase.core.commands.*
import com.advance.domain.firebase.prefs.Prefs
import com.advance.firebase.campaign.PromotionManager
import com.advance.firebase.core.remoteConfig.AdvanceRemoteConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseCommandsModule {

    @Provides
    @Singleton
    fun provideFirebaseDatabaseCommand(
        @ApplicationContext context: Context,
        dateOfFirstInstallCommand: DateOfFirstInstallCommand,
        firstInstallCommand: FirstInstallCommand,
        firebaseMobileIdCommand: FirebaseMobileIdCommand
    ): FirebaseDatabaseCommand {
        return FirebaseDatabaseCommand(
            context,
            dateOfFirstInstallCommand,
            firstInstallCommand,
            firebaseMobileIdCommand
        )
    }

    @Provides
    @Singleton
    fun provideFirebaseRemoteConfigCommand(
        advanceRemoteConfig: AdvanceRemoteConfig,
    ): FirebaseRemoteConfigCommand {
        return FirebaseRemoteConfigCommand(
            advanceRemoteConfig
        )
    }

    @Provides
    @Singleton
    fun provideFirstInstallCommand(
        prefs: Prefs,
        affiliateInfo: AffiliateInfo
    ): FirstInstallCommand {
        return FirstInstallCommand(
            prefs, affiliateInfo
        )
    }

    @Provides
    @Singleton
    fun provideDateOfFirstInstallCommand(
        prefs: Prefs
    ): DateOfFirstInstallCommand {
        return DateOfFirstInstallCommand(
            prefs
        )
    }

    @Provides
    @Singleton
    fun provideCampaignDaysCounterCommand(
        @ApplicationContext context: Context,
        prefs: Prefs
    ): CampaignDaysCounterCommand {
        return CampaignDaysCounterCommand(
            context, prefs
        )
    }

    @Provides
    @Singleton
    fun provideDebugRemoveCampaigns(
        @ApplicationContext context: Context
    ): DebugRemoveCampaigns {
        return DebugRemoveCampaigns(
            context
        )
    }

    @Provides
    @Singleton
    fun provideActiveCampaignCommand(
        trialCampaignManagement: PromotionManager
    ): ActiveCampaignCommand {
        return ActiveCampaignCommand(
            trialCampaignManagement
        )
    }

    @Provides
    @Singleton
    fun provideCampaignStatusCommand(
        @ApplicationContext context: Context,
        prefs: Prefs
    ): CampaignStatusCommand {
        return CampaignStatusCommand(context, prefs)
    }

    @Provides
    @Singleton
    fun provideMinArticleViewCommand(
        @ApplicationContext context: Context,
        prefs: Prefs
    ): MinArticleViewCommand {
        return MinArticleViewCommand(context, prefs)
    }

    @Provides
    @Singleton
    fun provideSyncProfileWithFirebaseCommand(
        prefs: Prefs,
        affiliateInfo: AffiliateInfo
    ): SyncProfileWithFirebaseCommand {
        return SyncProfileWithFirebaseCommand(prefs, affiliateInfo)
    }

    @Provides
    @Singleton
    fun provideFirebaseMobileIdCommand(
        @ApplicationContext context: Context,
        prefs: Prefs
    ): FirebaseMobileIdCommand {
        return FirebaseMobileIdCommand(context, prefs)
    }
}
