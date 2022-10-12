rootProject.name = "replicated-log"

include("master", "stub", "protos")

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }
}
