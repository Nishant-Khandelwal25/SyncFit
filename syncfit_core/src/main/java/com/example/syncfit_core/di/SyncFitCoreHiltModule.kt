package com.example.syncfit_core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room3.Room
import com.example.syncfit_core.api.network.ApiService
import com.example.syncfit_core.api.repository.ExerciseInfoRepository
import com.example.syncfit_core.api.repository.ExerciseInfoRepositoryImpl
import com.example.syncfit_core.constants.SyncFitCoreConstants.BASE_URL
import com.example.syncfit_core.constants.SyncFitCoreConstants.DATA_STORE_KEY
import com.example.syncfit_core.constants.SyncFitCoreConstants.EXERCISE_SESSION_DB
import com.example.syncfit_core.healthconnect.repository.HealthConnectRepository
import com.example.syncfit_core.healthconnect.repository.HealthConnectRepositoryImpl
import com.example.syncfit_core.localRepository.SyncFitDBRepository
import com.example.syncfit_core.localRepository.SyncFitDBRepositoryImpl
import com.example.syncfit_core.localRepository.SyncFitStorageLocalRepository
import com.example.syncfit_core.localRepository.SyncFitStorageLocalRepositoryImpl
import com.example.syncfit_core.room.dao.ExerciseInfoDao
import com.example.syncfit_core.room.dao.ExerciseSessionDao
import com.example.syncfit_core.room.db.ExerciseSessionDB
import com.example.syncfit_core.room.migration.Migration.MIGRATION_1_2
import com.example.syncfit_core.room.migration.Migration.MIGRATION_2_3
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
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
        return Room.databaseBuilder(context, ExerciseSessionDB::class.java, EXERCISE_SESSION_DB)
            .addMigrations(MIGRATION_1_2).addMigrations(MIGRATION_2_3).build()
    }

    @Provides
    @Singleton
    fun getExerciseSessionDao(database: ExerciseSessionDB): ExerciseSessionDao {
        return database.exerciseSessionDao()
    }

    @Provides
    @Singleton
    fun getExerciseInfoDao(database: ExerciseSessionDB): ExerciseInfoDao {
        return database.exerciseInfoDao()
    }

    @Provides
    @Singleton
    fun provideSyncFitExerciseSessionRepository(exerciseSessionDao: ExerciseSessionDao): SyncFitDBRepository =
        SyncFitDBRepositoryImpl(exerciseSessionDao)

    @Provides
    @Singleton
    fun provideHealthConnectRepository(@ApplicationContext context: Context): HealthConnectRepository =
        HealthConnectRepositoryImpl(context)

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .callTimeout(45, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson)).build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideNetworkRepository(apiService: ApiService, gson: Gson, dao: ExerciseInfoDao): ExerciseInfoRepository =
        ExerciseInfoRepositoryImpl(apiService, gson, dao)
}
