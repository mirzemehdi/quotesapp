plugins {
    id(Plugins.multiPlatformComposePlugin)
    id(Plugins.multiPlatformResources)
}

dependencies {
    commonMainApi(Libs.mokoResourcesCompose) // for compose multiplatform
    commonMainApi(Libs.mokoResources)
}

multiplatformResources {
    multiplatformResourcesPackage = "com.mmk.common.ui"
//    multiplatformResourcesVisibility = dev.icerock.gradle.MRVisibility.Internal // optional, default Public
}
