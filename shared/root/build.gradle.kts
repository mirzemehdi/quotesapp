plugins {
    id(Plugins.multiPlatformComposePlugin)
    id(Plugins.multiPlatformResources) // it is important to add this where cocoapods is added
    kotlin("native.cocoapods")
}

kotlin {
    cocoapods {
        version = "1.0.0"
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../../iosApp/Podfile")
        framework {
            baseName = "shared"
            isStatic = true
        }
//        extraSpecAttributes["resources"] =
//            "[" +
//                    "'build/cocoapods/framework/shared.framework/*.bundle', " +
//                    "'src/commonMain/resources/**', " +
//                    "'src/iosMain/resources/**', " +
//                    "'../features/quotes/src/commonMain/resources/**', " +
//                    "'../features/quotes/src/iosMain/resources/**', " +
//                    "]"
        extraSpecAttributes["resource"] = "'build/cocoapods/framework/shared.framework/*.bundle'"
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Libs.navigationMultiPlatform)
                implementation(project(Modules.core))
                api(project(Modules.commonUi))
                implementation(project(Modules.profile))
                implementation(project(Modules.quotes))
            }
        }
    }
}
