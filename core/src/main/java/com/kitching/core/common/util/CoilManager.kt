package com.kitching.core.common.util

import android.content.Context
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.disk.DiskCache
import coil3.disk.directory
import coil3.memory.MemoryCache

object CoilManager {
    //Default Memory Size = 0.15 ~ 0.2
    const val COIL_MEMORY_CACHE_SIZE_PERCENT = 0.15

    //Coil Dist Cache Size Setting
    const val COIL_DISK_CACHE_DIR_NAME = "coil_file_cache"
    const val COIL_DISK_CACHE_MAX_SIZE = 1024 * 1024 * 30

    private lateinit var applicationContext: Context

    fun initialize(context: Context) {
        applicationContext = context.applicationContext
    }

    fun createImageLoader(context: PlatformContext): ImageLoader =
        ImageLoader.Builder(context)
            .memoryCache {
                MemoryCache.Builder()
                    .maxSizePercent(context, COIL_MEMORY_CACHE_SIZE_PERCENT)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(context.filesDir.resolve(COIL_DISK_CACHE_DIR_NAME))
                    .maximumMaxSizeBytes(COIL_DISK_CACHE_MAX_SIZE.toLong())
                    .build()
            }
            .build()

    fun getContext(): PlatformContext {
        return applicationContext
    }
}