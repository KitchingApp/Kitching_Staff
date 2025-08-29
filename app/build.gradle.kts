plugins {
    alias(libs.plugins.kitching.android.application)
    alias(libs.plugins.kitching.android.compose)
    alias(libs.plugins.kitching.android.hilt)
    alias(libs.plugins.kitching.android.firebase.app)
    alias(libs.plugins.kitching.android.datastore)
    alias(libs.plugins.kitching.android.coil)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.secrets.gradle.plugin)
}

android {
    namespace = "com.kitching.app"

    defaultConfig {
        applicationId = "com.kitching.app.staff"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        buildConfig = true
    }
}

secrets {
    propertiesFileName = "local.properties"
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":core"))
    implementation(project(":feature:login"))
    implementation(project(":feature:main"))

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.sdk.v2.user)
}