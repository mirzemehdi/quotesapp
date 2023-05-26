plugins {
    id(Plugins.multiPlatformComposePlugin)
    id(Plugins.multiPlatformResources)
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(Modules.core))
                implementation(project(Modules.sharedCommonUi))
            }
        }
    }

}

dependencies {
    commonMainApi(Libs.mokoResources)
    commonMainApi(Libs.mokoResourcesCompose) // for compose multiplatform
}

multiplatformResources {
    multiplatformResourcesPackage = "com.mmk.features.profile" // required
    multiplatformResourcesVisibility = dev.icerock.gradle.MRVisibility.Internal // optional, default Public
}