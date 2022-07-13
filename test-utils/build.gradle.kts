plugins {
    id(Plugins.commonAndroidLibrary)
}

dependencies {
    implementation(TestingLibs.coroutine)
    implementation(TestingLibs.junit5JupiterApi)
}
