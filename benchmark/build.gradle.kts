import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.cheonjaeung.compose.grid.benchmark"
    compileSdk = 36

    defaultConfig {
        minSdk = 23

        testInstrumentationRunner = "androidx.benchmark.junit4.AndroidBenchmarkRunner"
        testInstrumentationRunnerArguments["androidx.benchmark.suppressErrors"] = "EMULATOR"
    }

    // Run instrumented tests against the release build type for accurate benchmark numbers.
    testBuildType = "release"

    buildTypes {
        release {
            isMinifyEnabled = true
            // Benchmarks are not shipped to production. Reuse the debug signing key.
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    buildFeatures {
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
}

dependencies {
    androidTestImplementation(libs.androidx.benchmark.junit4)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.compose.android.foundation)
    androidTestImplementation(libs.compose.android.ui.test.junit4)
    androidTestReleaseImplementation(libs.compose.android.ui.test.manifest)

    androidTestImplementation(project(":grid"))
}
