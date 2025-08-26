plugins {
    alias(libs.plugins.kitching.android.library)
    alias(libs.plugins.kitching.android.compose)
    alias(libs.plugins.kitching.android.firebase.data)
    alias(libs.plugins.kitching.android.coil)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.kitching.core"
}

dependencies {
    implementation(project(":domain"))

    implementation(libs.kotlinx.serialization.json)
}