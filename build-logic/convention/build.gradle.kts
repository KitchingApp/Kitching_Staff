import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "com.kitching.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.hilt.gradlePlugin)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "kitching.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "kitching.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidCompose") {
            id = "kitching.android.compose"
            implementationClass = "AndroidComposeConventionPlugin"
        }
        register("androidHilt") {
            id = "kitching.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }
        register("androidFirebaseApp") {
            id = "kitching.android.firebase.app"
            implementationClass = "AndroidFirebaseAppConventionPlugin"
        }
        register("androidFirebaseData") {
            id = "kitching.android.firebase.data"
            implementationClass = "AndroidFirebaseDataConventionPlugin"
        }
        register("androidCoil") {
            id = "kitching.android.coil"
            implementationClass = "AndroidCoilConventionPlugin"
        }
        register("androidDatastore") {
            id = "kitching.android.datastore"
            implementationClass = "AndroidDataStoreConventionPlugin"
        }
        register("androidRoom") {
            id = "kitching.android.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }
    }
}