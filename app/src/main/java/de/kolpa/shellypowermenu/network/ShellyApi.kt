package de.kolpa.shellypowermenu.network

import com.android.volley.RequestQueue
import com.google.gson.Gson
import de.kolpa.shellypowermenu.config.settings.ShellyApiSettings
import de.kolpa.shellypowermenu.network.requests.ShellyGetAllDevicesRequest
import de.kolpa.shellypowermenu.network.requests.ShellyGetAllStatusRequest
import de.kolpa.shellypowermenu.network.responses.ShellyGetAllDevicesResponse
import de.kolpa.shellypowermenu.network.responses.ShellyGetAllStatusResponse
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resumeWithException

class ShellyApi(
    private val requestQueue: RequestQueue,
    private val shellyApiSettings: ShellyApiSettings,
    private val gson: Gson
) {
    suspend fun getAllDevices() =
        suspendCancellableCoroutine<ShellyGetAllDevicesResponse> { continuation ->
            val apiKey = shellyApiSettings.shellyApiKey
            val baseUrl = shellyApiSettings.shellyBaseUrl

            if (apiKey?.isNotBlank() == true && baseUrl?.isNotBlank() == true) {
                val request = ShellyGetAllDevicesRequest(
                    gson = gson,
                    baseUrl = baseUrl,
                    apiKey = apiKey,
                    continuation = continuation
                )

                requestQueue.add(request)
            } else {
                continuation.resumeWithException(Exception("Shelly account not configured"))
            }
        }

    suspend fun getAllStatus() =
        suspendCancellableCoroutine<ShellyGetAllStatusResponse> { continuation ->
            val apiKey = shellyApiSettings.shellyApiKey
            val baseUrl = shellyApiSettings.shellyBaseUrl

            if (apiKey?.isNotBlank() == true && baseUrl?.isNotBlank() == true) {
                val request = ShellyGetAllStatusRequest(
                    gson = gson,
                    baseUrl = baseUrl,
                    apiKey = apiKey,
                    continuation = continuation
                )

                requestQueue.add(request)
            } else {
                continuation.resumeWithException(Exception("Shelly account not configured"))
            }
        }
}
