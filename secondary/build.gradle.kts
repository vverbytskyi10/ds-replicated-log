plugins {
    application
    kotlin("jvm")
    kotlin("plugin.serialization") version "1.7.20"
    id("io.ktor.plugin") version "2.1.2"
}

group = "com.decert"
version = "0.0.1"

application {
    mainClass.set("com.decert.master.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

dependencies {
    implementation(project(":stub"))
    implementation(project(":shared"))

    implementation("io.ktor:ktor-server-core-jvm:${Versions.ktor}")
    implementation("io.ktor:ktor-server-netty-jvm:${Versions.ktor}")
    implementation("ch.qos.logback:logback-classic:${Versions.logback}")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:${Versions.ktor}")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:${Versions.ktor}")
    implementation("ch.qos.logback:logback-classic:${Versions.logback}")

    runtimeOnly("io.grpc:grpc-netty:${Versions.grpc}")
}