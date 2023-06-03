plugins {
    id(Plugins.multiPlatformComposePlugin)
    kotlin(Plugins.kotlinSerialization) version "1.8.21"
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(Modules.core))
                implementation(project(Modules.commonUi))
                implementation(Libs.firebaseFireStoreMultiPlatform)
                implementation(Libs.kotlinSerializer)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(Libs.navigationCompose)
            }
        }
    }
}
android {
    defaultConfig {
        testInstrumentationRunner = "com.mmk.testutils.koin.KoinTestRunner"
    }
}
