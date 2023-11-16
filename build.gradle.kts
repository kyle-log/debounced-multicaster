import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.22"
    kotlin("plugin.spring") version "1.8.22"

    id("org.springframework.boot") version "3.1.5"
    id("io.spring.dependency-management") version "1.1.3"

    idea
}

group = "com.cocomo"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

apply {
    plugin("base")
    plugin("kotlin")
    plugin("kotlin-spring")
    plugin("io.spring.dependency-management")
    plugin("idea")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-amqp")
    implementation("org.springframework.boot:spring-boot-autoconfigure")
    implementation("org.springframework.boot:spring-boot-starter-data-redis:3.1.3")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")

    implementation("org.apache.logging.log4j:log4j-to-slf4j:2.20.0")
    implementation("org.apache.logging.log4j:log4j-api:2.20.0")
    implementation("io.github.oshai:kotlin-logging:5.1.0")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
