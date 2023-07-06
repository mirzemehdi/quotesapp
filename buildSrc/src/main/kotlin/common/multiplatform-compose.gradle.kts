package common

import ConfigData
import Libs
import Modules
import TestingLibs

plugins {
    id("com.android.library")
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("plugins.ktlint")
    id("plugins.detekt")
    id("de.mannodermaus.android-junit5")

}


kotlin {
    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

//    iosX64()
//    iosArm64()
    ios()
    iosSimulatorArm64()


    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Libs.coroutineCore)
                implementation(Libs.koinCore)
                implementation(Libs.napier)
                implementation(Libs.dateTime)

                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.material) //TODO remove this after fixing bottom nav
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(TestingLibs.coroutine)
                implementation(TestingLibs.turbine)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(Libs.androidXCore)
                implementation(Libs.androidxAppcompat)
                implementation(Libs.viewModelLifecycle)
                implementation(Libs.composeLifeCycleRuntime)
                implementation(Libs.koinCompose)
            }
        }
        val androidUnitTest by getting {
            dependencies {
                implementation(project(Modules.testUtils))
                implementation(TestingLibs.junit4)
                implementation(TestingLibs.truth)
                implementation(TestingLibs.junit5JupiterApi)
                runtimeOnly(TestingLibs.junit5JupiterEngine)
                runtimeOnly(TestingLibs.junit5VintageEngine)
                implementation(TestingLibs.mockk)
                implementation(TestingLibs.coroutine)
                implementation(TestingLibs.robolectric)
                implementation(TestingLibs.androidXCore)
                implementation(TestingLibs.androidXCoreKtx)
                implementation(TestingLibs.androidXRunner)
                implementation(TestingLibs.androidXJunit)
                implementation(TestingLibs.androidXJunitKtx)

            }
        }

        val androidInstrumentedTest by getting {
            dependencies {
                implementation(platform(Libs.composeBom))
                implementation(TestingLibs.composeUiJunit4)

            }
        }

        val iosMain by getting
        val iosSimulatorArm64Main by getting {
            dependsOn(iosMain)
        }

        val iosTest by getting
        val iosSimulatorArm64Test by getting {
            dependsOn(iosTest)
        }
//        val iosX64Main by getting
//        val iosArm64Main by getting
//        val iosSimulatorArm64Main by getting
//        val iosMain by creating {
//            dependsOn(commonMain)
//            iosX64Main.dependsOn(this)
//            iosArm64Main.dependsOn(this)
//            iosSimulatorArm64Main.dependsOn(this)
//        }
//        val iosX64Test by getting
//        val iosArm64Test by getting
//        val iosSimulatorArm64Test by getting
//        val iosTest by creating {
//            dependsOn(commonTest)
//            iosX64Test.dependsOn(this)
//            iosArm64Test.dependsOn(this)
//            iosSimulatorArm64Test.dependsOn(this)
//        }
    }
}

android {
    namespace = ConfigData.appId
    compileSdk = ConfigData.compileSdkVersion
    defaultConfig({
        minSdk = ConfigData.minSdkVersion
        targetSdk = ConfigData.targetSdkVersion
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFile("consumer-rules.pro")
    })

    packagingOptions(Action {
        with(resources.excludes) {
            add("META-INF/DEPENDENCIES")
            add("META-INF/LICENSE")
            add("META-INF/LICENSE.txt")
            add("META-INF/license.txt")
            add("META-INF/NOTICE")
            add("META-INF/notice.txt")
            add("META-INF/NOTICE")
            add("META-INF/NOTICE.txt")
            add("META-INF/LGPL2.1")
            add("META-INF/ASL2.0")
            add("META-INF/AL2.0")
            add("META-INF/*.kotlin_module")
            add("MR/base/strings.xml")
        }
    })
//
    testOptions(Action {
        animationsDisabled = true  // Animations are disabled for testing purposes
        unitTests.isIncludeAndroidResources = true
        unitTests.isReturnDefaultValues = true
    })

    buildTypes {
        val debug by getting {
            //TODO for some reason jacoco plugin has some problems when enable testCoverage
            //isTestCoverageEnabled = true

            dependencies {
                implementation(TestingLibs.composeUiManifest)
            }
        }
    }
    compileOptions({
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    })

    buildFeatures {
        buildConfig = false
    }

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

}
