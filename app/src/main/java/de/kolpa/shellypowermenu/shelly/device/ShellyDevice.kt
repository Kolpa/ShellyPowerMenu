package de.kolpa.shellypowermenu.shelly.device

import android.app.PendingIntent
import android.service.controls.Control
import de.kolpa.shellypowermenu.network.responses.ShellyGetAllStatusResponse

interface ShellyDevice {
    val deviceId: String
    val deviceName: String
    val room: String?

    fun updateState(deviceStatus: ShellyGetAllStatusResponse.Data.DeviceStatus)

    fun toStatelessControl(pendingIntent: PendingIntent): Control
    fun toStatefulControl(pendingIntent: PendingIntent): Control
}