package de.kolpa.shellypowermenu.shelly.provider

import android.service.controls.actions.ControlAction
import de.kolpa.shellypowermenu.shelly.device.ShellyDevice
import kotlinx.coroutines.flow.Flow

interface ShellyDeviceProvider {
    fun getDevicesFlow(): Flow<ShellyDevice>
    fun getDeviceStateFlow(deviceIds: List<String>): Flow<ShellyDevice>
    suspend fun updateDeviceWithAction(deviceId: String, action: ControlAction)
}