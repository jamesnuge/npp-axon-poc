plugins {
    kotlin("jvm") version "1.9.24"
    kotlin("plugin.spring") version "1.9.24"
    id("org.springframework.boot") version "3.2.5"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("plugin.noarg") version "2.3.20"
}

noArg {
    annotation("jakarta.persistence.Entity")
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

repositories {
    mavenCentral()
}


dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.axonframework:axon-spring-boot-starter:4.13.0")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    implementation("org.flywaydb:flyway-core")

    runtimeOnly("org.postgresql:postgresql")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}