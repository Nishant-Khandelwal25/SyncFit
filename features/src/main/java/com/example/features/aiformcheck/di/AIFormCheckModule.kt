package com.example.features.aiformcheck.di

import android.content.Context
import com.example.features.aiformcheck.data.pose.MediaPipePoseDetector
import com.example.features.aiformcheck.data.pose.PoseDetector
import com.example.features.aiformcheck.domain.repository.PoseRepository
import com.example.features.aiformcheck.domain.repository.PoseRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AIFormCheckModule {

    @Provides
    @Singleton
    fun providePoseDetector(@ApplicationContext context: Context): PoseDetector {
        return MediaPipePoseDetector(context)
    }

    @Provides
    @Singleton
    fun providePoseRepository(poseDetector: PoseDetector): PoseRepository {
        return PoseRepositoryImpl(poseDetector)
    }
}
