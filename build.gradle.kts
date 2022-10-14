plugins {
    kotlin("jvm") version "1.7.20" apply false
    id("com.google.protobuf") version "0.9.1" apply false
}

allprojects {
    repositories {
        mavenLocal()
        mavenCentral()
        google()
    }
}
