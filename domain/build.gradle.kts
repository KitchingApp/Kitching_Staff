plugins {
    alias(libs.plugins.kitching.android.library)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "com.kitching.domain"
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
}