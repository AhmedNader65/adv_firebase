package com.advance.firebase.core.di

import com.advance.domain.firebase.RemoteConfigService
import com.advance.firebase.core.FirebaseRepository
import com.advance.firebase.core.commands.FirebaseDatabaseCommand
import com.advance.firebase.core.commands.FirebaseRemoteConfigCommand
import com.advance.firebase.core.remoteConfig.AdvanceRemoteConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseRepository(
        firebaseDatabaseCommand: FirebaseDatabaseCommand,
        firebaseRemoteConfigCommand: FirebaseRemoteConfigCommand
    ): FirebaseRepository {
        return FirebaseRepository(
            firebaseDatabaseCommand, firebaseRemoteConfigCommand
        )
    }

    @Provides
    @Singleton
    fun provideAdvanceRemoteConfig(): AdvanceRemoteConfig {
        return AdvanceRemoteConfig()
    }

    @Provides
    fun provideRemoteConfigService(
        advanceRemoteConfig: AdvanceRemoteConfig
    ): RemoteConfigService = advanceRemoteConfig
}
