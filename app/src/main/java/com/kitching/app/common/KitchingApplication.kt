package com.kitching.app.common

import android.app.Application
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import com.kakao.sdk.common.KakaoSdk
import com.kitching.app.BuildConfig
import com.kitching.app.fcm.NoticeNotificationChannel
import com.kitching.app.fcm.ScheduleRejectedNotificationChannel
import com.kitching.core.common.util.CoilManager

class KitchingApplication: Application(), SingletonImageLoader.Factory {
    override fun onCreate() {
        super.onCreate()
        kitchingApplication = this

        KakaoSdk.init(this, BuildConfig.KAKAO_APP_KEY)
        ScheduleRejectedNotificationChannel().createChannel(this)
        NoticeNotificationChannel().createChannel(this)
        CoilManager.initialize(this)
    }

    override fun newImageLoader(context: PlatformContext): ImageLoader = CoilManager.createImageLoader(context)

    companion object {
        private lateinit var kitchingApplication: KitchingApplication
        fun getInstance(): KitchingApplication {
            return kitchingApplication
        }
    }
}