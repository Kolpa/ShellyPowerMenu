package de.kolpa.shellypowermenu.shelly.provider

import de.kolpa.shellypowermenu.shelly.device.ShellyDevice
import kotlinx.coroutines.flow.Flow

interface ShellyDeviceProvider {
    fun getDevicesFlow(): Flow<ShellyDevice>
    fun getDeviceStateFlow(deviceIds: List<String>): Flow<ShellyDevice>
}