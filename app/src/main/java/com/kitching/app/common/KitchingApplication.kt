package com.kitching.app.common

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.kitching.app.BuildConfig

class KitchingApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        kitchingApplication = this

        KakaoSdk.init(this, BuildConfig.KAKAO_APP_KEY)
    }

    companion object {
        private lateinit var kitchingApplication: KitchingApplication
        fun getInstance(): KitchingApplication {
            return kitchingApplication
        }
    }
}