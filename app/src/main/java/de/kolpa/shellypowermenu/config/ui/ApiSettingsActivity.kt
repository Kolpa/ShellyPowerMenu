package de.kolpa.shellypowermenu.config.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import de.kolpa.shellypowermenu.databinding.ApiSettingsActivityBinding
import org.koin.android.viewmodel.ext.android.viewModel

class ApiSettingsActivity : AppCompatActivity() {
    private lateinit var binding: ApiSettingsActivityBinding
    private val viewModel: ApiSettingsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ApiSettingsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            backendUrlTextView.addTextChangedListener {
                viewModel.setApiUrl(it.toString())
            }
            apiKeyTextView.addTextChangedListener {
                viewModel.setApiKey(it.toString())
            }
        }

        with(viewModel) {
            apiKey.observe(this@ApiSettingsActivity) {
                binding.apiKeyTextView.setText(it)
            }
            apiUrl.observe(this@ApiSettingsActivity) {
                binding.backendUrlTextView.setText(it)
            }
        }
    }
}