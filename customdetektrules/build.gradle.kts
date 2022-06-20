plugins {
    id("kotlin")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}

dependencies {
    compileOnly(Libs.detektCustomApi)
    implementation(Libs.detektCli)

    testImplementation(TestingLibs.detekt)
    testImplementation(TestingLibs.assertj)
    testImplementation(TestingLibs.junit4)
    testImplementation(TestingLibs.truth)
}