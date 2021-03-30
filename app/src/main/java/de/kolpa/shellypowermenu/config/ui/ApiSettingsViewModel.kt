package de.kolpa.shellypowermenu.config.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import de.kolpa.shellypowermenu.config.settings.ShellyApiSettings
import kotlinx.coroutines.flow.MutableStateFlow

class ApiSettingsViewModel(
    private val shellyApiSettings: ShellyApiSettings
) : ViewModel() {
    private val apiUrlFlow = MutableStateFlow(shellyApiSettings.shellyBaseUrl)
    private val apiKeyFlow = MutableStateFlow(shellyApiSettings.shellyApiKey)

    val apiUrl = apiUrlFlow.asLiveData()
    val apiKey = apiKeyFlow.asLiveData()

    fun setApiUrl(apiUrl: String) {
        shellyApiSettings.shellyBaseUrl = apiUrl
    }

    fun setApiKey(apiKey: String) {
        shellyApiSettings.shellyApiKey = apiKey
    }
}