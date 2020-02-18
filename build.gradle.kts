import Build_gradle.DependencyVersions.arrowVersion

plugins {
    kotlin("jvm") version "1.3.61"
}

group = "io.saagie"
version = "1.0-SNAPSHOT"

repositories {
    maven { setUrl("https://dl.bintray.com/kotlin/kotlin-eap") }
    mavenCentral()
}

object DependencyVersions {
    // Dependencies
    const val arrowVersion = "0.10.4"
    // TestsDependencies
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("io.arrow-kt:arrow-core:$arrowVersion")
    implementation("io.arrow-kt:arrow-fx:$arrowVersion")
    implementation("io.arrow-kt:arrow-syntax:$arrowVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.3")
    implementation(kotlin("script-runtime"))
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}
