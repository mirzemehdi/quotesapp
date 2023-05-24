plugins {
    id(Plugins.multiPlatformComposePlugin)
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(Modules.core))
            }
        }
    }

}