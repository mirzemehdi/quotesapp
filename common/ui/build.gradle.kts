plugins {
    id(Plugins.commonAndroidLibrary)
}
android {
    buildFeatures {
        viewBinding = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.4"
    }
}

dependencies {
    api(Libs.material)
    implementation(Libs.androidxAppcompat)
    api(Libs.paging)
    api(Libs.constraintLayout)
    implementJetpackComposeUi()
}
