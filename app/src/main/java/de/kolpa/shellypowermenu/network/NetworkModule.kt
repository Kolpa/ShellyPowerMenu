package de.kolpa.shellypowermenu.network

import com.android.volley.RequestQueue
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.NoCache
import com.google.gson.Gson
import org.koin.dsl.module

val networkModule = module {
    single { buildVolleyRequestQueue() }
    single { Gson() }
    single { ShellyApi(get(), get(), get()) }
}

fun buildVolleyRequestQueue(): RequestQueue {
    val cache = NoCache()
    val network = BasicNetwork(HurlStack())

    return RequestQueue(cache, network).apply {
        start()
    }
}