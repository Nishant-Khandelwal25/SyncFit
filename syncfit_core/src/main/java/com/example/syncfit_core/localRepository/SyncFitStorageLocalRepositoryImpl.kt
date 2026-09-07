package com.example.syncfit_core.localRepository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.syncfit_core.localRepository.SyncFitStorageLocalRepositoryImpl.Keys.USERNAME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SyncFitStorageLocalRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) :
    SyncFitStorageLocalRepository {
    private object Keys {
        val HAS_ONBOARDED = booleanPreferencesKey("has_onboarded")
        val HAS_ASKED_CAMERA_PERMISSION = booleanPreferencesKey("has_asked_camera_permission")
        val USERNAME = stringPreferencesKey("username")
    }

    override suspend fun setUserHasOnboarded(hasOnboarded: Boolean) {
        dataStore.edit { it[Keys.HAS_ONBOARDED] = hasOnboarded }
    }

    override suspend fun getUserHasOnboarded(): Boolean {
        return dataStore.data.first()[Keys.HAS_ONBOARDED] ?: false
    }

    override suspend fun setHasRequestedCameraPermission(hasRequested: Boolean) {
        dataStore.edit { it[Keys.HAS_ASKED_CAMERA_PERMISSION] = hasRequested }
    }

    override suspend fun getHasRequestedCameraPermission(): Boolean {
        return dataStore.data.first()[Keys.HAS_ASKED_CAMERA_PERMISSION] ?: false
    }

    override suspend fun loginUser(username: String) {
        dataStore.edit {
            it[USERNAME] = username.trim()
        }
    }

    override val username: Flow<String?>
        get() =
            dataStore.data.catch { exception -> if (exception is IOException) emit(emptyPreferences()) else throw exception }
                .map { preferences ->
                    preferences[USERNAME]?.trim()?.takeIf { it.isNotEmpty() }
                }

    override suspend fun logoutUser() {
        dataStore.edit { it.remove(USERNAME) }
    }
}
