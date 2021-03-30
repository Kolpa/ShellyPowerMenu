package de.kolpa.shellypowermenu.config

import de.kolpa.shellypowermenu.config.settings.ShellyApiSettings
import de.kolpa.shellypowermenu.config.ui.ApiSettingsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val configModule = module {
    single { ShellyApiSettings(get()) }
    viewModel { ApiSettingsViewModel(get()) }
}