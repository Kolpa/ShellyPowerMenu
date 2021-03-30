package de.kolpa.shellypowermenu

import android.app.Application
import de.kolpa.shellypowermenu.config.configModule
import de.kolpa.shellypowermenu.network.networkModule
import de.kolpa.shellypowermenu.shelly.shellyModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ShellyPowerMenuApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@ShellyPowerMenuApplication)
            modules(shellyModule, configModule, networkModule)
        }
    }
}