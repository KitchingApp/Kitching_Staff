import java.util.Properties

plugins {
    alias(libs.plugins.kitching.android.application)
    alias(libs.plugins.kitching.android.compose)
    alias(libs.plugins.kitching.android.hilt)
    alias(libs.plugins.kitching.android.firebase.app)
    alias(libs.plugins.kitching.android.datastore)
    alias(libs.plugins.kitching.android.coil)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.google.gms.google.services)
}

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(localPropertiesFile.inputStream())
}

android {
    namespace = "com.kitching.app"

    defaultConfig {
        applicationId = "com.kitching.app.staff"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField(
            "String",
            "KAKAO_APP_KEY",
            "\"${localProperties.getProperty("KAKAO_APP_KEY")}\""
        )
        manifestPlaceholders["KAKAO_APP_KEY"] = localProperties["KAKAO_APP_KEY"] as Any
    }

    buildFeatures {
        buildConfig = true
    }
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