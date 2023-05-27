plugins {
    id(Plugins.multiPlatformComposePlugin)
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(Modules.core))
                implementation(project(Modules.sharedCommonUi))
            }
        }

        val androidMain by getting{
            dependencies{
                implementation(Libs.navigationCompose)
            }
        }
    }

}