import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.android.library)
    alias(libs.plugins.maven.publish)
}

kotlin {
    androidTarget()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "ComposeGridLayout"
        }
    }

    jvm {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.compose.multiplatform.runtime)
                implementation(libs.compose.multiplatform.foundation)
                implementation(libs.compose.multiplatform.ui.util)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.androidx.core)
                implementation(libs.compose.android.runtime)
                implementation(libs.compose.android.foundation)
                implementation(libs.compose.android.ui.util)
            }
        }

        val androidInstrumentedTest by getting {
            dependencies {
                implementation(libs.junit4)
                implementation(libs.androidx.activity.compose)
                implementation(libs.compose.android.ui.test.junit4)
                implementation(libs.compose.android.ui.test.manifest)
            }
        }
    }
}

android {
    namespace = "${project.group}"
    compileSdk = 35

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

    defaultConfig {
        minSdk = 21
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
    signAllPublications()
    coordinates("${project.group}", "grid", "${project.version}")

    pom {
        name.set("GridLayout for Compose")
        description.set("Missing grid layout for Jetpack Compose and Compose Multiplatform.")
        url.set("https://github.com/cheonjaeung/gridlayout-compose")

        licenses {
            license {
                name.set("Apache License, Version 2.0")
                url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }

        developers {
            developer {
                id.set("cheonjaeung")
                name.set("Jaeung Cheon")
                email.set("cheonjaewoong@gmail.com")
            }
        }

        scm {
            url.set("https://github.com/cheonjaeung/gridlayout-compose")
            connection.set("scm:git:git://github.com/cheonjaeung/gridlayout-compose.git")
            developerConnection.set("scm:git:ssh://git@github.com/cheonjaeung/gridlayout-compose.git")
        }
    }
}
