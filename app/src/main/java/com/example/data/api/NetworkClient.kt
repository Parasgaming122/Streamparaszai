package com.example.data.api

import android.content.Context
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.io.File
import java.util.concurrent.TimeUnit

object NetworkClient {
    @Volatile
    private var clientInstance: OkHttpClient? = null

    val client: OkHttpClient
        get() = clientInstance ?: synchronized(this) {
            clientInstance ?: OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build().also { clientInstance = it }
        }

    fun initialize(context: Context) {
        synchronized(this) {
            val current = clientInstance
            if (current == null || current.cache == null) {
                val cacheSize = 50 * 1024 * 1024L // 50 MiB
                val cacheDir = File(context.cacheDir, "http_cache")
                clientInstance = OkHttpClient.Builder()
                    .cache(Cache(cacheDir, cacheSize))
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(15, TimeUnit.SECONDS)
                    .build()
            }
        }
    }
}
