group = "org.example"
version = "1.0-SNAPSHOT"

plugins {
    java
    id("org.springframework.boot") version "2.4.5" apply true
    id("io.spring.dependency-management") version "1.0.11.RELEASE" apply true
    id("org.siouan.frontend-jdk11") version "5.1.0" apply true
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-rest")
    implementation("org.springframework.boot:spring-boot-devtools")

    testImplementation("org.springframework.boot:spring-boot-starter-test")

    implementation("com.h2database:h2")

    // lombok
    implementation("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")
}

/*
tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
*/

tasks.withType<Test> {
    useJUnitPlatform()
}

frontend {
    nodeVersion.set("16.1.0")
    nodeInstallDirectory.set(project.layout.projectDirectory.dir("node_modules"))
}