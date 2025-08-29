plugins {
    alias(libs.plugins.kitching.android.library)
    alias(libs.plugins.kitching.android.compose)
    alias(libs.plugins.kitching.android.hilt)
    alias(libs.plugins.kitching.android.coil)
    alias(libs.plugins.kitching.android.datastore)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.kitching.login"
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":core"))

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.sdk.v2.user)
}