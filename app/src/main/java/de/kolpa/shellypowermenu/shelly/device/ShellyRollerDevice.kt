package de.kolpa.shellypowermenu.shelly.device

import android.app.PendingIntent
import android.service.controls.Control
import android.service.controls.DeviceTypes
import android.service.controls.templates.RangeTemplate
import de.kolpa.shellypowermenu.network.responses.ShellyGetAllStatusResponse

data class ShellyRollerDevice(
    override val deviceId: String,
    override val deviceName: String,
    override val room: String?,
    var openState: Float?
) : ShellyDevice {
    override fun updateState(deviceStatus: ShellyGetAllStatusResponse.Data.DeviceStatus) {
        openState = deviceStatus.rollers[0].posAsFloat
    }

    override fun toStatelessControl(pendingIntent: PendingIntent): Control =
        Control.StatelessBuilder(deviceId, pendingIntent)
            .setTitle(deviceName)
            .setSubtitle(room ?: "Unknown Room")
            .setDeviceType(DeviceTypes.TYPE_BLINDS)
            .build()

    override fun toStatefulControl(pendingIntent: PendingIntent): Control {
        val rangeTemplate = RangeTemplate(
            "${deviceId}-range",
            0f,
            1f,
            openState ?: 0f,
            0.01f,
            null
        )

        return Control.StatefulBuilder(deviceId, pendingIntent)
            .setTitle(deviceName)
            .setSubtitle(room ?: "Unknown Room")
            .setDeviceType(DeviceTypes.TYPE_BLINDS)
            .setStatus(Control.STATUS_OK)
            .setControlTemplate(rangeTemplate)
            .build()
    }
}