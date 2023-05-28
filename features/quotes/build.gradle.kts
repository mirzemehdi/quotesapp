plugins {
    id(Plugins.commonAndroidLibrary)
}

dependencies {
    implementation(project(Modules.core))
    implementation(project(Modules.commonUi))
    implementation(project(Modules.sharedQuotes))

    implementFirebase()
}

android {

    defaultConfig {
        testInstrumentationRunner = "com.mmk.testutils.koin.KoinTestRunner"
    }
}
