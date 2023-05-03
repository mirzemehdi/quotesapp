plugins {
    id(Plugins.commonAndroidLibrary)
}

dependencies {
    api(Libs.material)
    implementation(Libs.androidxAppcompat)
    api(Libs.paging)
    api(Libs.pagingCompose)
    api(Libs.constraintLayout)
}
