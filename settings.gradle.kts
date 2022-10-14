rootProject.name = "replicated-log"

include("shared", "protos", "stub", "master", "secondary")

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }
}
