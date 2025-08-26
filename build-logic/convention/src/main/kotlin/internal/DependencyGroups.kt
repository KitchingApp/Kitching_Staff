package internal

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.addAndroidCoreDependencies() {
    dependencies {
        add("implementation", libs.findBundle("android-core-libraries").get())
    }
}

internal fun Project.addTestDependencies() {
    dependencies {
        add("testImplementation", libs.findBundle("test-libraries").get())
        add("androidTestImplementation", libs.findBundle("android-test-libraries").get())
    }
}