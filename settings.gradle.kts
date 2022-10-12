rootProject.name = "replicated-log"

include("protos", "stub", "master", "server")

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }
}
