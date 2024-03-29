object Libs {

    //AndroidX
    val androidXCore by lazy { "androidx.core:core-ktx:${Versions.androidXCore}" }

    val viewModelLifecycle by lazy { "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.viewModelLifecyle}" }


    //Retrofit and Moshi convertor - Networking libraries
    val moshi by lazy { "com.squareup.moshi:moshi:${Versions.moshi}" }
    val moshiKotlin by lazy { "com.squareup.moshi:moshi-kotlin:${Versions.moshi}" }
    val retrofit2 by lazy { "com.squareup.retrofit2:retrofit:${Versions.retrofit2}" }
    val retrofit2Converter by lazy { "com.squareup.retrofit2:converter-moshi:${Versions.retrofit2}" }
    val okHttpLogger by lazy { "com.squareup.okhttp3:logging-interceptor:${Versions.okHttpLogging}" }

    //Koin - Dependency Injection
    val koinCore by lazy { "io.insert-koin:koin-core:${Versions.koin}" }
    val koinAndroid by lazy { "io.insert-koin:koin-android:${Versions.koin}" }
    val koinCompose by lazy { "io.insert-koin:koin-androidx-compose:${Versions.koinCompose}" }



    //Coroutine - For background threading
    val coroutineCore by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutine}" }
    val coroutineAndroid by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutine}" }

    //Detekt - Static code analyzing
    val detektCustomApi by lazy { "io.gitlab.arturbosch.detekt:detekt-api:${Versions.detekt}" }
    val detektCli by lazy { "io.gitlab.arturbosch.detekt:detekt-cli:${Versions.detekt}" }

    //Firebase
    val firebasePlatform by lazy { "com.google.firebase:firebase-bom:${Versions.firebase}" }
    val firebaseFireStore by lazy { "com.google.firebase:firebase-firestore" }
    val firebaseFireStoreKtx by lazy { "com.google.firebase:firebase-firestore-ktx" }
    val firebaseAnalytics by lazy { "com.google.firebase:firebase-analytics-ktx" }
    val firebaseCrashlytics by lazy { "com.google.firebase:firebase-crashlytics" }

    // coroutines support for firebase operations
    val firebaseCoroutineSupport by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:${Versions.coroutine}" }

    //For logging
    val timber by lazy { "com.jakewharton.timber:timber:${Versions.timber}" }

    //Pagination
    val paging by lazy { "androidx.paging:paging-runtime:${Versions.paging}" }
    val pagingCompose by lazy { "androidx.paging:paging-compose:${Versions.pagingCompose}" }

    //MaterialLibrary
    val material by lazy { "com.google.android.material:material:${Versions.material}" }
    val androidxAppcompat by lazy { "androidx.appcompat:appcompat:${Versions.androidxAppcompat}" }
    val constraintLayout by lazy { "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}" }
    val circleImageView by lazy { "de.hdodenhof:circleimageview:${Versions.circleImageView}" }

    val navigationFragment by lazy { "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}" }
    val navigationUi by lazy { "androidx.navigation:navigation-ui-ktx:${Versions.navigation}" }



    //Jetpack Compose
    val composeBom by lazy { "androidx.compose:compose-bom:${Versions.composeBom}" }
    val composeRuntime by lazy { "androidx.compose.runtime:runtime:1.4.0" }
    val composeMaterial3 by lazy { "androidx.compose.material3:material3" }
    val composeMaterial2 by lazy { "androidx.compose.material:material" }
    val composeFoundation by lazy { "androidx.compose.foundation:foundation" }
    val composeUi by lazy { "androidx.compose.ui:ui" }
    val composeToolingPreview by lazy { "androidx.compose.ui:ui-tooling-preview" }
    val composeUiTooling by lazy { "androidx.compose.ui:ui-tooling" }
    val composeLiveData by lazy { "androidx.compose.runtime:runtime-livedata" }
    val composeActivity by lazy { "androidx.activity:activity-compose:${Versions.androidXActivity}" }

    val composeLifeCycleViewModel by lazy { "androidx" +
            ".lifecycle:lifecycle-viewmodel-compose:${Versions.androidXLifecycle}" }
    val composeLifeCycleRuntime by lazy { "androidx.lifecycle:lifecycle-runtime-compose:${Versions.androidXLifecycle}" }
    val navigationCompose by lazy { "androidx.navigation:navigation-compose:${Versions
        .navigation}" }



    //KMM
    //Resource sharing
    val mokoResources by lazy { "dev.icerock.moko:resources:${Versions.mokoResources}" }
    val mokoResourcesCompose by lazy { "dev.icerock.moko:resources-compose:${Versions.mokoResources}" }

    //Logging
    val napier by lazy { "io.github.aakira:napier:${Versions.napier}" }
    //Date
    val dateTime by lazy { "org.jetbrains.kotlinx:kotlinx-datetime:${Versions.dateTime}" }

    //Kotlin serializer
    val kotlinSerializer by lazy { "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.kotlinSerializer}" }
    //Navigation
    val navigationMultiPlatform by lazy { "com.chrynan.navigation:navigation-compose:${Versions.navigationMultiPlatform}" }

    //Ktor
    val ktorCore by lazy { "io.ktor:ktor-client-core:${Versions.ktor}" }
    val ktorCioEngine by lazy { "io.ktor:ktor-client-cio:${Versions.ktor}" }
    val ktorLogging by lazy { "io.ktor:ktor-client-logging:${Versions.ktor}" }
    val ktorSerialization by lazy { "io.ktor:ktor-serialization-kotlinx-json:${Versions.ktor}" }
    val ktorContentNegotiation by lazy { "io.ktor:ktor-client-content-negotiation:${Versions.ktor}" }
    val ktorResources by lazy { "io.ktor:ktor-client-resources:${Versions.ktor}" }
    val ktorWebSocket by lazy { "io.ktor:ktor-client-websockets:${Versions.ktor}" }
    val ktorDarwinEngine by lazy { "io.ktor:ktor-client-darwin:${Versions.ktor}" }
    val logbackClassic by lazy { "ch.qos.logback:logback-classic:1.2.11" }


}


object Plugins {
    val commonAndroidLibrary by lazy { "common.android-library" }
    val googleServices by lazy { "com.google.gms.google-services" }
    val firebaseCrashlytics by lazy { "com.google.firebase.crashlytics" }

    val multiPlatformComposePlugin by lazy { "common.multiplatform-compose" }
    val multiPlatformResources by lazy { "dev.icerock.mobile.multiplatform-resources" }
    val kotlinSerialization by lazy { "plugin.serialization" }
}

object TestingLibs {
    val junit4 by lazy { "junit:junit:${Versions.jUnit}" }
    val truth by lazy { "com.google.truth:truth:${Versions.truth}" }
    val junit5JupiterApi by lazy { "org.junit.jupiter:junit-jupiter-api:${Versions.jUnit5}" }
    val junit5JupiterEngine by lazy { "org.junit.jupiter:junit-jupiter-engine:${Versions.jUnit5}" }
    val junit5VintageEngine by lazy { "org.junit.vintage:junit-vintage-engine:${Versions.jUnit5}" }
    val mockk by lazy { "io.mockk:mockk:${Versions.mockk}" }
    val coroutine by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutine}" }
    val detekt by lazy { "io.gitlab.arturbosch.detekt:detekt-test:${Versions.detekt}" }
    val robolectric by lazy { "org.robolectric:robolectric:${Versions.robolectric}" }
    val turbine by lazy { "app.cash.turbine:turbine:${Versions.turbine}" } //Flow testing
    val composeUiJunit4 by lazy { "androidx.compose.ui:ui-test-junit4" }
    val composeUiManifest by lazy { "androidx.compose.ui:ui-test-manifest" }


    //Needed for testing detekt custom rules
    val assertj by lazy { "org.assertj:assertj-core:${Versions.assertj}" }


    // Instrumented test libraries androidTestImplementation(...)
    val androidXCore by lazy { "androidx.test:core:${Versions.androidXTestCore}" }
    val androidXCoreKtx by lazy { "androidx.test:core-ktx:${Versions.androidXTestCore}" }
    val androidXRunner by lazy { "androidx.test:runner:${Versions.androidXTestCore}" }
    val androidXJunit by lazy { "androidx.test.ext:junit:${Versions.androidXJunit}" }
    val androidXJunitKtx by lazy { "androidx.test.ext:junit-ktx:${Versions.androidXJunit}" }
    val mockkAndroid by lazy { "io.mockk:mockk-android:${Versions.mockk}" }
    val fragmentTesting by lazy { "androidx.fragment:fragment-testing:${Versions.fragmentTesting}" }
    val espresso by lazy { "androidx.test.espresso:espresso-core:${Versions.espresso}" }



}