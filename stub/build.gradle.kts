import com.google.protobuf.gradle.id

plugins {
    kotlin("jvm")
    id("com.google.protobuf")
}

dependencies {
    protobuf(project(":protos"))

    api(kotlin("stdlib"))

    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}")

    api("io.grpc:grpc-protobuf:${Versions.grpc}")
    api("com.google.protobuf:protobuf-java-util:${Versions.protobuf}")
    api("io.grpc:grpc-kotlin-stub:${Versions.grpcKotlin}")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

sourceSets {
    val main by getting {}
    main.java.srcDirs("build/generated/source/proto/main/grpc")
    main.java.srcDirs("build/generated/source/proto/main/grpckt")
    main.java.srcDirs("build/generated/source/proto/main/java")
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:${Versions.protobuf}"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:${Versions.grpc}"
        }
        id("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:${Versions.grpcKotlin}:jdk8@jar"
        }
    }
    generateProtoTasks {
        all().forEach {
            it.plugins {
                id("grpc")
                id("grpckt")
            }
        }
    }
}
