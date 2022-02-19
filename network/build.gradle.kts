plugins {
    id("com.android.library")
    kotlin("android")
    id("kotlin-android")
    id(Plugins.junit5)
}

android {
    compileSdk = ConfigData.compileSdkVersion

    defaultConfig {
        minSdk = ConfigData.minSdkVersion
        targetSdk = ConfigData.targetSdkVersion

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFile("consumer-rules.pro")
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