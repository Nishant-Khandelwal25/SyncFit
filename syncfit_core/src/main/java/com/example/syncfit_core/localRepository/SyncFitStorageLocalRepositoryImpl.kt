package com.example.syncfit_core.localRepository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.first
import javax.inject.Singleton

@Singleton
class SyncFitStorageLocalRepositoryImpl(
    private val dataStore: DataStore<Preferences>,
) :
    SyncFitStorageLocalRepository {
    private object Keys {
        val HAS_ONBOARDED = booleanPreferencesKey("has_onboarded")
    }

    override suspend fun setUserHasOnboarded(hasOnboarded: Boolean) {
        dataStore.edit { it[Keys.HAS_ONBOARDED] = hasOnboarded }
    }

    override suspend fun getUserHasOnboarded(): Boolean {
        return dataStore.data.first()[Keys.HAS_ONBOARDED] ?: false
    }
}
