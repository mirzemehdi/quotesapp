package common

import ConfigData
import Libs
import TestingLibs
import implementJetpackComposeUi
import org.jetbrains.compose.compose

plugins {
    id("com.android.library")
    kotlin("multiplatform")
    id("org.jetbrains.compose")
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
    listOf(iosX64(), iosArm64(), iosSimulatorArm64()).forEach {
        it.binaries.framework {
            baseName = "QuotesAppBaseFramework"
        }
    }

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
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(Libs.androidXCore)
                implementation(Libs.androidxAppcompat)
                api(Libs.pagingCompose)
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
//
    //TODO for some reason jacoco plugin has some problems when enable testCoverage
//    buildTypes({
//        getByName("debug") {
//            isTestCoverageEnabled = true
//        }
//    })
//
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
