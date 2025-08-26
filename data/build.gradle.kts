plugins {
    alias(libs.plugins.kitching.android.library)
    alias(libs.plugins.kitching.android.datastore)
    alias(libs.plugins.kitching.android.firebase.data)
    alias(libs.plugins.kitching.android.hilt)
    alias(libs.plugins.kitching.android.room)
}

android {
    namespace = "com.kitching.data"
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":core"))
}