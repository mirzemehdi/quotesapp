plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

repositories {
    mavenCentral()
    google()
    gradlePluginPortal()
    maven("https://plugins.gradle.org/m2/")
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev/")
}

object PluginsVersions {
    const val gradlePlugin = "7.4.2"
    const val kotlin = "1.8.10"
    const val jUnit5GradlePlugin = "1.8.2.1"
    const val jacoco = "0.8.7" // Testing Coverage report
    const val ktlint = "10.2.1" // Ktlint - Static Code analyzer
    const val detekt = "1.19.0" // Detekt - Static Code analyzer
    const val jetpackCompose = "1.4.0"
}

dependencies {
    // android gradle plugin
    implementation("com.android.tools.build:gradle:${PluginsVersions.gradlePlugin}")
    // kotlin plugin
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${PluginsVersions.kotlin}")
    implementation("de.mannodermaus.gradle.plugins:android-junit5:${PluginsVersions.jUnit5GradlePlugin}")
    implementation("org.jacoco:org.jacoco.core:${PluginsVersions.jacoco}")
    implementation("org.jlleitschuh.gradle:ktlint-gradle:${PluginsVersions.ktlint}")
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:${PluginsVersions.detekt}")
    implementation("org.jetbrains.compose:compose-gradle-plugin:${PluginsVersions.jetpackCompose}")
}