package com.example.syncfit_core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room3.Room
import com.example.syncfit_core.constants.SyncFitCoreConstants.DATA_STORE_KEY
import com.example.syncfit_core.constants.SyncFitCoreConstants.EXERCISE_SESSION_DB
import com.example.syncfit_core.localRepository.SyncFitDBRepository
import com.example.syncfit_core.localRepository.SyncFitDBRepositoryImpl
import com.example.syncfit_core.localRepository.SyncFitStorageLocalRepository
import com.example.syncfit_core.localRepository.SyncFitStorageLocalRepositoryImpl
import com.example.syncfit_core.room.dao.ExerciseSessionDao
import com.example.syncfit_core.room.db.ExerciseSessionDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SyncFitCoreHiltModule {

    @Provides
    @Singleton
    fun provideSyncFitDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            produceFile = { context.preferencesDataStoreFile(DATA_STORE_KEY) },
        )

    @Provides
    @Singleton
    fun provideSyncFitLocalRepository(
        dataStore: DataStore<Preferences>,
    ): SyncFitStorageLocalRepository = SyncFitStorageLocalRepositoryImpl(dataStore)

    @Provides
    @Singleton
    fun provideExerciseSessionDB(@ApplicationContext context: Context): ExerciseSessionDB {
        return Room.databaseBuilder(context, ExerciseSessionDB::class.java, EXERCISE_SESSION_DB).build()
    }

    @Provides
    @Singleton
    fun getExerciseSessionDao(database: ExerciseSessionDB): ExerciseSessionDao {
        return database.exerciseSessionDao()
    }

    @Provides
    @Singleton
    fun provideSyncFitExerciseSessionRepository(exerciseSessionDao: ExerciseSessionDao): SyncFitDBRepository =
        SyncFitDBRepositoryImpl(exerciseSessionDao)
}
