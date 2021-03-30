package de.kolpa.shellypowermenu.services

import android.app.PendingIntent
import android.content.Intent
import android.service.controls.Control
import android.service.controls.ControlsProviderService
import android.service.controls.actions.ControlAction
import android.util.Log
import de.kolpa.shellypowermenu.shelly.provider.ShellyDeviceProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.jdk9.asPublisher
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.util.concurrent.Flow
import java.util.function.Consumer
import kotlin.coroutines.CoroutineContext

class ShellyPowerMenuControlsProviderService : ControlsProviderService(), CoroutineScope {
    private val supervisorJob = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + supervisorJob

    private val shellyDeviceProvider by inject<ShellyDeviceProvider>()

    override fun createPublisherForAllAvailable(): Flow.Publisher<Control> =
        shellyDeviceProvider.getDevicesFlow()
            .catch {
                Log.d(
                    "ShellyPowerMenuControlsProviderService",
                    "failed to fetch devices"
                )
            }.map {
                val pendingIntent = PendingIntent.getActivity(
                    baseContext,
                    CONTROL_REQUEST_CODE,
                    Intent(),
                    PendingIntent.FLAG_UPDATE_CURRENT
                )

                it.toStatelessControl(pendingIntent)
            }.asPublisher()


    override fun createPublisherFor(controlIds: MutableList<String>): Flow.Publisher<Control> =
        shellyDeviceProvider.getDeviceStateFlow(controlIds)
            .catch {
                Log.d(
                    "ShellyPowerMenuControlsProviderService",
                    "error in device state flow"
                )
            }
            .map {
                val pendingIntent = PendingIntent.getActivity(
                    baseContext,
                    CONTROL_REQUEST_CODE,
                    Intent(),
                    PendingIntent.FLAG_UPDATE_CURRENT
                )

                it.toStatefulControl(pendingIntent)
            }.asPublisher()

    override fun performControlAction(
        controlId: String,
        action: ControlAction,
        consumer: Consumer<Int>
    ) {
        consumer.accept(ControlAction.RESPONSE_OK)

        launch {
            shellyDeviceProvider.updateDeviceWithAction(controlId, action)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        supervisorJob.cancel()
    }

    companion object {
        private const val CONTROL_REQUEST_CODE = 1
    }
}