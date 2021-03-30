package de.kolpa.shellypowermenu.shelly

import de.kolpa.shellypowermenu.shelly.provider.DefaultShellyDeviceProvider
import de.kolpa.shellypowermenu.shelly.provider.ShellyDeviceProvider
import org.koin.dsl.module

val shellyModule = module {
    single<ShellyDeviceProvider> { DefaultShellyDeviceProvider(get()) }
}