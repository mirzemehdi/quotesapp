plugins {
    id(Plugins.commonAndroidLibrary)
}

dependencies {
    implementation(project(Modules.core))
    implementation(project(Modules.commonUi))

    implementation(platform(Libs.firebasePlatform))
    implementation(Libs.firebaseFireStore)
    implementation(Libs.firebaseFireStoreKtx)
    implementation(Libs.firebaseCoroutineSupport)
    implementation(Libs.paging)
    implementation(Libs.circleImageView)
}

android {

    defaultConfig {
        testInstrumentationRunner = "com.mmk.testutils.koin.KoinTestRunner"
    }
}
