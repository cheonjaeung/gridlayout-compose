import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlinx.binary.compatibility.validator)
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.compose.multiplatform) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.paparazzi) apply false
    alias(libs.plugins.maven.publish) apply false
}

allprojects {
    group = "com.cheonjaeung.compose.grid"
    version = "2.6.0"

    repositories {
        google()
        mavenCentral()
    }
}

subprojects {
    tasks.withType<KotlinCompile> {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
}

apiValidation {
    // :samples:android, :samples:shared
    ignoredProjects.addAll(listOf("android", "shared"))

    nonPublicMarkers.add("kotlin.PublishedApi")
}
