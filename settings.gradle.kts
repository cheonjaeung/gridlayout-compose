pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "gridlayout-compose"

include(":grid")
include(":benchmark")
include(":samples:shared")
include(":samples:android")
