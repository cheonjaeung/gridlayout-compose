pluginManagement {
    repositories {
        maven {
            name = "JetBrains"
            url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        }
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "compose-grid"

include(":grid")
