package com.example.praktikum1

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

// Provider
class UserPreferences(context: Context) {
    private val dataStore = context.dataStore

    companion object {
        val EMAIL_KEY = stringPreferencesKey("email")
        val NAME_KEY = stringPreferencesKey("name")
        val AGE_KEY = intPreferencesKey("age")
        val PHONE_KEY = stringPreferencesKey("phone")
        val PASSWORD_KEY = stringPreferencesKey("password")
    }
    // Save Data
    suspend fun saveUserData(
        email: String,
        name: String,
        age: Int,
        phone: String,
        password: String) {
        dataStore.edit { preferences ->
            preferences[EMAIL_KEY] = email
            preferences[NAME_KEY] = name
            preferences[AGE_KEY] = age
            preferences[PHONE_KEY] = phone
            preferences[PASSWORD_KEY] = password
        }
    }
    // Get Data
    val userFlow: Flow<User> = dataStore.data.map { preferences ->
        User(
            email = preferences[EMAIL_KEY] ?: "",
            name = preferences[NAME_KEY] ?: "",
            age = preferences[AGE_KEY] ?: 0,
            phone = preferences[PHONE_KEY] ?: "",
            password = preferences[PASSWORD_KEY] ?: ""
        )
    }
    // Validation
    suspend fun isEmailRegistered(email: String, password: String): Boolean {
        val preferences = dataStore.data.first()
        return preferences[EMAIL_KEY] == email && preferences[PASSWORD_KEY] == password
    }
    // clearData
    suspend fun clearUserData() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

}
// Data Model
data class User(
    val email: String,
    val name: String,
    val age: Int,
    val phone: String,
    val password: String
)

