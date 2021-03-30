package de.kolpa.shellypowermenu.network.requests

import com.android.volley.NetworkResponse
import com.android.volley.ParseError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.google.gson.Gson
import de.kolpa.shellypowermenu.network.responses.ShellySetRollerPosResponse
import kotlinx.coroutines.CancellableContinuation
import java.nio.charset.Charset
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class ShellySetRollerPosRequest(
    private val gson: Gson,
    baseUrl: String,
    private val apiKey: String,
    private val deviceId: String,
    private val rollerPosition: Int,
    private val continuation: CancellableContinuation<ShellySetRollerPosResponse>
) : Request<ShellySetRollerPosResponse>(
    Method.POST,
    baseUrl + DEVICE_LIST_ENDPOINT,
    { err -> continuation.resumeWithException(err) }
) {
    init {
        continuation.invokeOnCancellation { cancel() }
    }

    override fun getParams(): MutableMap<String, String> =
        mutableMapOf(
            "auth_key" to apiKey,
            "channel" to DEFAULT_CHANNEL,
            "roller_id" to DEFAULT_ROLLER_ID,
            "id" to deviceId,
            "pos" to rollerPosition.toString()
        )

    override fun parseNetworkResponse(response: NetworkResponse?): Response<ShellySetRollerPosResponse> {
        return try {
            val json = String(
                response?.data ?: ByteArray(0),
                Charset.forName(
                    HttpHeaderParser.parseCharset(response?.headers)
                )
            )

            Response.success(
                gson.fromJson(json, ShellySetRollerPosResponse::class.java),
                HttpHeaderParser.parseCacheHeaders(response)
            )
        } catch (e: Exception) {
            Response.error(ParseError(e))
        }
    }

    override fun deliverResponse(response: ShellySetRollerPosResponse) {
        continuation.resume(response)
    }

    companion object {
        private const val DEFAULT_CHANNEL = "0"
        private const val DEFAULT_ROLLER_ID = "0"
        private const val DEVICE_LIST_ENDPOINT = "/device/relay/roller/settings/topos"
    }
}
