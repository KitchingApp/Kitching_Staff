import com.android.build.api.dsl.CommonExtension
import internal.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.plugin.compose")
            }

            val extension = extensions.getByName("android") as CommonExtension<*, *, *, *, *, *>
            extension.apply {
                buildFeatures {
                    compose = true
                }
            }

            dependencies {
                val bom = libs.findLibrary("androidx-compose-bom").get()
                add("implementation", platform(bom))
                add("implementation", libs.findBundle("compose-libraries").get())

                add("androidTestImplementation", platform(bom))
                add("androidTestImplementation", libs.findBundle("compose-test-libraries").get())
                add("debugImplementation", libs.findBundle("compose-debug-libraries").get())
            }
        }
    }
}