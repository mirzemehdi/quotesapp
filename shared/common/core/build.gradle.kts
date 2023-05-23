plugins {
//    id(Plugins.commonAndroidLibrary)
    id("com.android.library")
    kotlin("multiplatform")

}

kotlin {
    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    // Create root moduel include all shared modules and add this code
//    https://youtrack.jetbrains.com/issue/KT-42247
//    targets.filterIsInstance<KotlinNativeTarget>()
//        .filter { it.konanTarget.family == Family.IOS }
//        .forEach { target ->
//            target.binaries {
//                framework {
//                    baseName = "quotesShared"
//                }
//            }
//        }
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "core"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Libs.coroutineCore)
                implementation(Libs.koinCore)
                implementation(Libs.napier)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting{
            dependencies {
                implementation(Libs.androidXCore)
            }
        }
        val androidUnitTest by getting {
            dependencies {
                implementation(TestingLibs.junit4)
                implementation(TestingLibs.truth)
                implementation(TestingLibs.mockk)
                implementation(TestingLibs.robolectric)
                implementation(TestingLibs.androidXCoreKtx)
                implementation(TestingLibs.androidXJunitKtx)

            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    namespace = "com.mmk.quotesapp"
    compileSdk = 33
    defaultConfig {
        minSdk = 21
    }
}
