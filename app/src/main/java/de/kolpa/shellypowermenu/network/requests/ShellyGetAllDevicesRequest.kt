package de.kolpa.shellypowermenu.network.requests

import com.android.volley.NetworkResponse
import com.android.volley.ParseError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.google.gson.Gson
import de.kolpa.shellypowermenu.network.responses.ShellyGetAllDevicesResponse
import kotlinx.coroutines.CancellableContinuation
import java.nio.charset.Charset
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class ShellyGetAllDevicesRequest(
    private val gson: Gson,
    baseUrl: String,
    private val apiKey: String,
    private val continuation: CancellableContinuation<ShellyGetAllDevicesResponse>
) : Request<ShellyGetAllDevicesResponse>(
    Method.POST,
    baseUrl + DEVICE_LIST_ENDPOINT,
    { err -> continuation.resumeWithException(err) }
) {
    init {
        continuation.invokeOnCancellation { cancel() }
    }

    override fun getParams(): MutableMap<String, String> =
        mutableMapOf("auth_key" to apiKey)

    override fun parseNetworkResponse(response: NetworkResponse?): Response<ShellyGetAllDevicesResponse> {
        return try {
            val json = String(
                response?.data ?: ByteArray(0),
                Charset.forName(
                    HttpHeaderParser.parseCharset(response?.headers)
                )
            )

            Response.success(
                gson.fromJson(json, ShellyGetAllDevicesResponse::class.java),
                HttpHeaderParser.parseCacheHeaders(response)
            )
        } catch (e: Exception) {
            Response.error(ParseError(e))
        }
    }

    override fun deliverResponse(response: ShellyGetAllDevicesResponse) {
        continuation.resume(response)
    }

    companion object {
        private const val DEVICE_LIST_ENDPOINT = "/interface/device/get_all_lists"
    }
}
