import org.gradle.kotlin.dsl.`kotlin-dsl`

plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`

}

repositories {
    mavenCentral()
    google()
}

object PluginsVersions {
    const val gradlePlugin = "7.1.0"
    const val kotlin = "1.6.10"
    const val jUnit5GradlePlugin = "1.8.2.0"
    const val jacoco = "0.8.7" // Testing Coverage report
}

dependencies {
    // android gradle plugin
    implementation("com.android.tools.build:gradle:${PluginsVersions.gradlePlugin}")
    // kotlin plugin
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${PluginsVersions.kotlin}")
    implementation("de.mannodermaus.gradle.plugins:android-junit5:${PluginsVersions.jUnit5GradlePlugin}")
    implementation("org.jacoco:org.jacoco.core:${PluginsVersions.jacoco}")

}