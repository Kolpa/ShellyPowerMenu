package de.kolpa.shellypowermenu.config.settings

import android.content.Context
import androidx.core.content.edit

class ShellyApiSettings(private val appContext: Context) {
    private val prefs by lazy {
        appContext.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
    }

    var shellyBaseUrl: String?
        get() = prefs.getString(BASE_URL, null)
        set(value) = prefs.edit {
            putString(BASE_URL, value)
        }

    var shellyApiKey: String?
        get() = prefs.getString(BACKEND_API_KEY, null)
        set(value) = prefs.edit {
            putString(BACKEND_API_KEY, value)
        }

    companion object {
        private const val SHARED_PREFS_NAME = "shelly_settings"
        private const val BASE_URL = "backend.url"
        private const val BACKEND_API_KEY = "backend.api_key"
    }
}