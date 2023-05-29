plugins {
    id(Plugins.multiPlatformComposePlugin)
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(Modules.core))
                implementation(project(Modules.commonUi))
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(Libs.navigationCompose)
            }
        }
    }
}
