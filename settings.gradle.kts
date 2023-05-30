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
        maven("https://repo.repsy.io/mvn/chrynan/public")

    }
}


rootProject.name="Quotes App"
include(
    ":app",
    ":customdetektrules",
    ":test-utils",
    ":features:quotes",
    ":shared:common:core",
    ":shared:features:profile",
    ":shared:common:ui",
    ":shared:features:quotes",
    ":shared:root"
)
