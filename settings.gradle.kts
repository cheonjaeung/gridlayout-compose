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

rootProject.name = "gridlayout-compose"

include(":grid")
include(":sample-android", ":sample-desktop")
