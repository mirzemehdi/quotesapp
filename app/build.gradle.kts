

plugins {
    id("com.android.application")
    id("kotlin-android")
    id(Plugins.googleServices)
    id(Plugins.firebaseCrashlytics)
    id("plugins.jacoco-android")
    id("plugins.ktlint")
}

android {
    compileSdk = ConfigData.compileSdkVersion
    defaultConfig(
        Action {
            applicationId = ConfigData.appId
            minSdk = ConfigData.minSdkVersion
            targetSdk = ConfigData.targetSdkVersion
            versionCode = ConfigData.versionCode
            versionName = ConfigData.versionName
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    )

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        viewBinding = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.4"
    }
}

dependencies {
    implementation(project(Modules.profile))
    implementation(project(Modules.quotes))
    implementation(project(Modules.core))
    implementation(project(Modules.commonUi))

    implementation(Libs.constraintLayout)
    implementation(Libs.androidxAppcompat)
    implementation(Libs.material)
    implementation(Libs.navigationUi)
    implementation(Libs.navigationCompose)
    implementation(Libs.navigationFragment)
    implementation(Libs.koinAndroid)
    implementation(Libs.timber)
    implementFirebase()
    implementJetpackComposeUi()
}
