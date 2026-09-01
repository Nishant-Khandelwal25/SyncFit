package com.example.features.di

import com.example.features.aiformcheck.domain.usecase.AiFormCheckUseCase
import com.example.features.aiformcheck.domain.usecase.AiFormCheckUseCaseImpl
import com.example.features.home.usecase.HomeScreenUseCase
import com.example.features.home.usecase.HomeScreenUseCaseImpl
import com.example.features.onboarding.usecase.OnBoardingUseCase
import com.example.features.onboarding.usecase.OnBoardingUseCaseImpl
import com.example.features.workouthistory.domain.WorkoutHistoryUseCase
import com.example.features.workouthistory.domain.WorkoutHistoryUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface SyncFitFeaturesUseCaseModule {
    @Binds
    fun bindOnBoardingUseCase(impl: OnBoardingUseCaseImpl): OnBoardingUseCase

    @Binds
    fun bindHomeScreenUseCase(impl: HomeScreenUseCaseImpl): HomeScreenUseCase

    @Binds
    fun bindAiFormCheckUseCase(impl: AiFormCheckUseCaseImpl): AiFormCheckUseCase

    @Binds
    fun bindWorkoutHistoryUseCase(impl: WorkoutHistoryUseCaseImpl): WorkoutHistoryUseCase
}
