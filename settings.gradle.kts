import org.gradle.api.initialization.resolve.RepositoriesMode

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven("https://plugins.gradle.org/m2/")
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
        jcenter()
        gradlePluginPortal()
    }
}


rootProject.name="Quotes App"
include(
    ":app",
    ":network",
    ":customdetektrules",
    ":test-utils",
//    ":common:ui",
    ":features:quotes",
    ":shared:common:core",
    ":shared:features:profile",
    ":shared:common:ui"
)
