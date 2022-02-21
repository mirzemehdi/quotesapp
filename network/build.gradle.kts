plugins {
    id("com.android.library")
    kotlin("android")
    id("kotlin-android")
    id(Plugins.junit5)
    id(Plugins.jacoco_android)
}

android {
    compileSdk = ConfigData.compileSdkVersion

    defaultConfig {
        minSdk = ConfigData.minSdkVersion
        targetSdk = ConfigData.targetSdkVersion

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFile("consumer-rules.pro")
    }

    packagingOptions {
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
    }

    testOptions {
        animationsDisabled = true  // Animations are disabled for testing purposes
        unitTests.isIncludeAndroidResources = true
        unitTests.isReturnDefaultValues = true
    }

    buildTypes {
        debug {
            isTestCoverageEnabled = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementNetworkingLibraries()

    testImplementation(TestingLibs.junit4)
    testImplementation(TestingLibs.truth)
    testImplementation(TestingLibs.junit5JupiterApi)
    testRuntimeOnly(TestingLibs.junit5JupiterEngine)
    testRuntimeOnly(TestingLibs.junit5VintageEngine)

}