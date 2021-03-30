package de.kolpa.shellypowermenu.network.responses

import com.google.gson.annotations.SerializedName

// Currently we only define enough types to support rollers
data class ShellyGetAllStatusResponse(
    @SerializedName("isok") val isok: Boolean,
    @SerializedName("data") val data: Data
) {
    data class Data(
        @SerializedName("devices_status") val devices_status: Map<String, DeviceStatus>
    ) {
        data class DeviceStatus(
            @SerializedName("rollers") val rollers: List<Rollers>,
        ) {
            data class Rollers(
                @SerializedName("current_pos") val current_pos: Int
            ) {
                val posAsFloat: Float get() = current_pos.toFloat() / 100f
            }
        }
    }
}
