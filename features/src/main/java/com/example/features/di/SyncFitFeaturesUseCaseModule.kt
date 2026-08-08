package com.example.features.di

import com.example.features.onboarding.usecase.OnBoardingUseCase
import com.example.features.onboarding.usecase.OnBoardingUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface SyncFitFeaturesUseCaseModule {
    @Binds
    fun bindOnBoardingUseCase(impl: OnBoardingUseCaseImpl): OnBoardingUseCase
}
