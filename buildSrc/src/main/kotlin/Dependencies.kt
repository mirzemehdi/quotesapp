object Libs {

    //Retrofit and Moshi convertor - Networking libraries
    val moshi by lazy { "com.squareup.moshi:moshi:${Versions.moshi}" }
    val moshiKotlin by lazy { "com.squareup.moshi:moshi-kotlin:${Versions.moshi}" }
    val retrofit2 by lazy { "com.squareup.retrofit2:retrofit:${Versions.retrofit2}" }
    val retrofit2Converter by lazy { "com.squareup.retrofit2:converter-moshi:${Versions.retrofit2}" }
    val okHttpLogger by lazy { "com.squareup.okhttp3:logging-interceptor:${Versions.okHttpLogging}" }

    //Detekt - Static code analyzing
    val detektCustomApi by lazy {"io.gitlab.arturbosch.detekt:detekt-api:${Versions.detekt}"}
    val detektCli by lazy {"io.gitlab.arturbosch.detekt:detekt-cli:${Versions.detekt}"}
}


object Plugins {
    val commonAndroidLibrary by lazy { "common.android-library" }
}

object TestingLibs {
    val junit4 by lazy { "junit:junit:${Versions.jUnit}" }
    val truth by lazy { "com.google.truth:truth:${Versions.truth}" }
    val junit5JupiterApi by lazy { "org.junit.jupiter:junit-jupiter-api:${Versions.jUnit5}" }
    val junit5JupiterEngine by lazy { "org.junit.jupiter:junit-jupiter-engine:${Versions.jUnit5}" }
    val junit5VintageEngine by lazy { "org.junit.vintage:junit-vintage-engine:${Versions.jUnit5}" }
    val mockk by lazy { "io.mockk:mockk:${Versions.mockk}" }
    val mockkAndroid by lazy { "io.mockk:mockk-android:${Versions.mockk}" }

    val detekt by lazy { "io.gitlab.arturbosch.detekt:detekt-test:${Versions.detekt}" }

    //Needed for testing detekt custom rules
    val assertj by lazy { "org.assertj:assertj-core:${Versions.assertj}" }

}