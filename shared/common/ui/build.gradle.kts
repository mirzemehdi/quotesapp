plugins {
    id(Plugins.multiPlatformComposePlugin)
    id(Plugins.multiPlatformResources)

}

dependencies {
    commonMainApi(Libs.mokoResourcesCompose) // for compose multiplatform
}

multiplatformResources {
    multiplatformResourcesPackage = "com.mmk.common.ui"
    multiplatformResourcesVisibility = dev.icerock.gradle.MRVisibility.Internal // optional, default Public
}