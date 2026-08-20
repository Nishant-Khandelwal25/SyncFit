package com.example.features.aiformcheck.di

import com.example.features.aiformcheck.data.exercise.SquatConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ExerciseModule {
    @Provides
    fun provideSquatConfig(): SquatConfig {
        return SquatConfig()
    }
}
