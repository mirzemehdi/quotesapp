plugins {
    id(Plugins.commonAndroidLibrary)
}

dependencies {
    implementation(project(Modules.core))
    implementation(project(Modules.commonUi))

    implementFirebase()
    implementation(Libs.circleImageView)
}

android {

    defaultConfig {
        testInstrumentationRunner = "com.mmk.testutils.koin.KoinTestRunner"
    }
}
