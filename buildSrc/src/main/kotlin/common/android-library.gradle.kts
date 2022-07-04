package common

plugins {
    id("com.android.library")
    kotlin("android")
    id("de.mannodermaus.android-junit5")
    id("plugins.jacoco-android")
    id("plugins.ktlint")
    id("plugins.detekt")
}

android {
    compileSdk = ConfigData.compileSdkVersion


    defaultConfig(Action {
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
        }
    })

    testOptions(Action {
        animationsDisabled = true  // Animations are disabled for testing purposes
        unitTests.isIncludeAndroidResources = true
        unitTests.isReturnDefaultValues = true
    })

    buildTypes(Action {
        getByName("debug") {
            isTestCoverageEnabled = true
        }
    })

    compileOptions(Action {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8

    })
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }


}

dependencies {
    detekt(project(Modules.customDetektRules))
    implementation(Libs.koinAndroid)


    testImplementation(TestingLibs.junit4)
    testImplementation(TestingLibs.truth)
    testImplementation(TestingLibs.junit5JupiterApi)
    testRuntimeOnly(TestingLibs.junit5JupiterEngine)
    testRuntimeOnly(TestingLibs.junit5VintageEngine)
    testImplementation(TestingLibs.mockk)
    testImplementation(TestingLibs.coroutine)

    androidTestImplementation(TestingLibs.mockkAndroid)


}