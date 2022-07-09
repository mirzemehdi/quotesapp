
plugins {
    id(Plugins.commonAndroidLibrary)
}

dependencies {
    implementation(project(Modules.core))
    implementation(platform(Libs.firebasePlatform))
    implementation(Libs.firebaseFireStore)
    implementation(Libs.firebaseFireStoreKtx)
    implementation(Libs.firebaseCoroutineSupport)
}
