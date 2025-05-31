plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "${project.group}.sample.android"
    compileSdk = 35

    defaultConfig {
        applicationId = "${project.group}.sample.android"
        minSdk = 21
        targetSdk = 35
        versionCode = 1
        versionName = "${project.version}"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    implementation(libs.androidx.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.compose)

    implementation(libs.compose.android.runtime)
    implementation(libs.compose.android.foundation)
    implementation(libs.compose.android.ui)
    implementation(libs.compose.android.material3)

    implementation(project(":samples:shared"))
}
