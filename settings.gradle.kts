pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "gridlayout-compose"

include(":grid")
include(":samples:shared")
include(":samples:android")
