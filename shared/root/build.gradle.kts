plugins {
    id(Plugins.multiPlatformComposePlugin)
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(Libs.navigationMultiPlatform)
                api(project(Modules.core))
                implementation(project(Modules.commonUi))
                implementation(project(Modules.profile))
                implementation(project(Modules.quotes))
            }
        }
    }
}
