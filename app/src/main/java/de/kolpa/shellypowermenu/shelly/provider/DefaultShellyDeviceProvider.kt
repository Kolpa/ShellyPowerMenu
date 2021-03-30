package de.kolpa.shellypowermenu.shelly.provider

import de.kolpa.shellypowermenu.network.ShellyApi
import de.kolpa.shellypowermenu.shelly.device.ShellyDevice
import de.kolpa.shellypowermenu.shelly.device.ShellyRollerDevice
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive

class DefaultShellyDeviceProvider(
    private val shellyApi: ShellyApi
) : ShellyDeviceProvider {
    private val idToDevicesMap = mutableMapOf<String, ShellyDevice>()

    private suspend fun getAllShellyDevices(): List<ShellyDevice> {
        val rawListResponse = shellyApi.getAllDevices()

        // TODO: Currently only roller devices are supported
        return rawListResponse.data.devices.mapNotNull {
            when (it.value.category) {
                "roller" -> ShellyRollerDevice(
                    deviceId = it.value.id,
                    deviceName = it.value.name,
                    room = rawListResponse.data.rooms[it.value.room_id.toString()]?.name,
                    openState = null
                )
                else -> null
            }
        }.onEach {
            idToDevicesMap.putIfAbsent(it.deviceId, it)
        }
    }

    private suspend fun getDeviceForId(id: String): ShellyDevice? {
        val deviceFromMap = idToDevicesMap[id]

        if (deviceFromMap != null) {
            return deviceFromMap
        }

        getAllShellyDevices()

        return idToDevicesMap[id]
    }

    override fun getDevicesFlow(): Flow<ShellyDevice> = flow {
        emitAll(getAllShellyDevices().asFlow())
    }

    override fun getDeviceStateFlow(deviceIds: List<String>): Flow<ShellyDevice> = flow {
        while (currentCoroutineContext().isActive) {
            val rawStatusResponse = shellyApi.getAllStatus()

            val devices = rawStatusResponse.data.devices_status
                .filter { deviceIds.contains(it.key) }
                .mapNotNull {
                    val shellyDevice = getDeviceForId(it.key)

                    shellyDevice?.updateState(it.value)

                    shellyDevice
                }

            emitAll(devices.asFlow())

            delay(DEVICE_STATE_REFRESH_DELAY)
        }
    }

    companion object {
        private const val DEVICE_STATE_REFRESH_DELAY = 5000L
    }
}