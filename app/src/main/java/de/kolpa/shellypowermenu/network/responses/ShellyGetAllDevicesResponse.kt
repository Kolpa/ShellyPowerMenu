package de.kolpa.shellypowermenu.network.responses

import com.google.gson.annotations.SerializedName

// Currently we only define enough types to support rollers
data class ShellyGetAllDevicesResponse(
    @SerializedName("isok") val isok: Boolean,
    @SerializedName("data") val data: Data
) {
    data class Data(
        @SerializedName("devices") val devices: Map<String, Device>,
        @SerializedName("rooms") val rooms: Map<String, Room>
    ) {
        data class Room(
            @SerializedName("name") val name: String,
            @SerializedName("id") val id: Int
        )

        data class Device(
            @SerializedName("id") val id: String,
            @SerializedName("name") val name: String,
            @SerializedName("room_id") val room_id: Int,
            @SerializedName("category") val category: String
        )
    }
}
