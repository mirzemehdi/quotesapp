plugins {
    id(Plugins.commonAndroidLibrary)
}

dependencies {
    implementation(TestingLibs.coroutine)
    implementation(TestingLibs.androidXRunner)
    implementation(TestingLibs.junit5JupiterApi)
}
