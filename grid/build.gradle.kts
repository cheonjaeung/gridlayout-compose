import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.paparazzi)
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

    applyDefaultHierarchyTemplate()

    sourceSets {
        commonMain {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.uiUtil)
            }
        }
    }
}

android {
    namespace = "${project.group}"
    compileSdk = 36

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

afterEvaluate {
    tasks.named("test").configure {
        dependsOn("verifyPaparazzi")
    }
}

mavenPublishing {
    publishToMavenCentral()
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
