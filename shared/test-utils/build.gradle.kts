plugins {
    id(Plugins.multiPlatformComposePlugin)
}

kotlin {
    sourceSets {
        val androidMain by getting {
            dependencies {
                implementation(TestingLibs.coroutine)
                implementation(TestingLibs.androidXRunner)
                implementation(TestingLibs.junit5JupiterApi)
            }
        }
    }
}
