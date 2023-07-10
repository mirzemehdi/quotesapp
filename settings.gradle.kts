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
//    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
//    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        jcenter()
        gradlePluginPortal()
        maven("https://repo.repsy.io/mvn/chrynan/public")
        maven("https://plugins.gradle.org/m2/")
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev/")

    }
}


rootProject.name="QuotesApp"
include(
    ":app",
    ":desktopApp",
    ":customdetektrules",
    ":shared:test-utils",
    ":shared:common:core",
    ":shared:features:profile",
    ":shared:common:ui",
    ":shared:features:quotes",
    ":shared:root"
)
