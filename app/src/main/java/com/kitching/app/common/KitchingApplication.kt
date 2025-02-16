package com.kitching.app.common

import android.app.Application

class KitchingApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        kitchingApplication = this
    }

    companion object {
        private lateinit var kitchingApplication: KitchingApplication
        fun getInstance(): KitchingApplication {
            return kitchingApplication
        }
    }
}