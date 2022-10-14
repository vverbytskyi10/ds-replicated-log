rootProject.name = "replicated-log"

include("protos", "stub", "master", "secondary")

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }
}
include()
