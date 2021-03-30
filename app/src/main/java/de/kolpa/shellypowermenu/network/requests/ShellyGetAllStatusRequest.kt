package de.kolpa.shellypowermenu.network.requests

import com.android.volley.NetworkResponse
import com.android.volley.ParseError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.google.gson.Gson
import de.kolpa.shellypowermenu.network.responses.ShellyGetAllStatusResponse
import kotlinx.coroutines.CancellableContinuation
import java.nio.charset.Charset
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class ShellyGetAllStatusRequest(
    private val gson: Gson,
    baseUrl: String,
    private val apiKey: String,
    private val continuation: CancellableContinuation<ShellyGetAllStatusResponse>
) : Request<ShellyGetAllStatusResponse>(
    Method.POST,
    baseUrl + DEVICE_STATUS_ENDPOINT,
    { err -> continuation.resumeWithException(err) }
) {
    init {
        continuation.invokeOnCancellation { cancel() }
    }

    override fun getParams(): MutableMap<String, String> =
        mutableMapOf("auth_key" to apiKey)

    override fun parseNetworkResponse(response: NetworkResponse?): Response<ShellyGetAllStatusResponse> {
        return try {
            val json = String(
                response?.data ?: ByteArray(0),
                Charset.forName(
                    HttpHeaderParser.parseCharset(response?.headers)
                )
            )

            Response.success(
                gson.fromJson(json, ShellyGetAllStatusResponse::class.java),
                HttpHeaderParser.parseCacheHeaders(response)
            )
        } catch (e: Exception) {
            Response.error(ParseError(e))
        }
    }

    override fun deliverResponse(response: ShellyGetAllStatusResponse) {
        continuation.resume(response)
    }

    companion object {
        private const val DEVICE_STATUS_ENDPOINT = "/device/all_status"
    }
}
