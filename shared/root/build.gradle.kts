plugins {
    id(Plugins.multiPlatformComposePlugin)
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(Modules.core))
                api(project(Modules.commonUi))
                api(project(Modules.profile))
                api(project(Modules.quotes))
            }
        }
    }
}
