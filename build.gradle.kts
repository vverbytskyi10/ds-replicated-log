plugins {
    id("com.google.protobuf") version "0.9.1" apply false
    kotlin("jvm") version "1.7.20" apply false
}

ext["grpc_version"] = "1.49.1"
ext["grpc_kotlin_version"] = "1.3.0"
ext["protobuf_version"] = "3.20.3"

allprojects {
    repositories {
        mavenLocal()
        mavenCentral()
        google()
    }
}