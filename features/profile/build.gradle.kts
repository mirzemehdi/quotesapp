plugins {
    id(Plugins.commonAndroidLibrary)
    id("org.jetbrains.kotlin.android")
}

dependencies {
    implementation(project(Modules.core))
    implementation(project(Modules.commonUi))
}
