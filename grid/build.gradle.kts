import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.maven.publish)
}

kotlin {
    androidTarget()
    jvm("desktop")

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.compose.multiplatform.runtime)
                implementation(libs.compose.multiplatform.foundation)
            }
        }
        val commonTest by getting

        val androidMain by getting {
            dependencies {
                implementation(libs.androidx.core)
                implementation(libs.compose.android.runtime)
                implementation(libs.compose.android.foundation)
            }
        }
        val androidInstrumentedTest by getting {
            dependsOn(commonTest)
            dependencies {
                implementation(libs.junit4)
                implementation(libs.androidx.activity.compose)
                implementation(libs.compose.android.ui.test.junit4)
                implementation(libs.compose.android.ui.test.manifest)
            }
        }

        val desktopMain by getting {
            dependsOn(commonMain)
            dependencies {
                implementation(libs.compose.multiplatform.runtime)
                implementation(libs.compose.multiplatform.foundation)
            }
        }
    }
}

android {
    namespace = "${project.group}"
    compileSdk = 33

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

    defaultConfig {
        minSdk = 21
        targetSdk = 33
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.android.compiler.plugin.get()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.S01)
    signAllPublications()
    coordinates("${project.group}", "grid", "${project.version}")

    pom {
        name.set("grid")
        description.set("Missing grid layout for Jetpack Compose and Compose Multiplatform.")
        url.set("https://github.com/cheonjaewoong/gridlayout-compose")

        licenses {
            license {
                name.set("Apache License, Version 2.0")
                url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }

        developers {
            developer {
                id.set("cheonjaewoong")
                name.set("Jaewoong Cheon")
                email.set("cheonjaewoong@gmail.com")
            }
        }

        scm {
            url.set("https://github.com/cheonjaewoong/gridlayout-compose")
            connection.set("scm:git:git://github.com/cheonjaewoong/gridlayout-compose.git")
            developerConnection.set("scm:git:ssh://git@github.com/cheonjaewoong/gridlayout-compose.git")
        }
    }
}
