package de.kolpa.shellypowermenu.network.responses

import com.google.gson.annotations.SerializedName

data class ShellySetRollerPosResponse(
    @SerializedName("isok") val isok: Boolean
)
