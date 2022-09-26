plugins {
    id(Plugins.commonAndroidLibrary)
}
android {
    buildFeatures {
        viewBinding = true
    }
}
dependencies {
    api(Libs.material)
    implementation(Libs.androidxAppcompat)
    api(Libs.paging)
    api(Libs.constraintLayout)
}
