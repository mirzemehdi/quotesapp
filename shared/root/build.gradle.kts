plugins {
    id(Plugins.multiPlatformComposePlugin)
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
            // export(project(Modules.core))
        }
        extraSpecAttributes["resources"] =
            "['src/commonMain/resources/**', 'src/iosMain/resources/**']"
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Libs.navigationMultiPlatform)
                implementation(project(Modules.core))
                implementation(project(Modules.commonUi))
                implementation(project(Modules.profile))
                implementation(project(Modules.quotes))
            }
        }

        val androidMain by getting {
            dependencies {
                api(Libs.navigationMultiPlatform)
                api(project(Modules.core))
            }
        }
    }
}
