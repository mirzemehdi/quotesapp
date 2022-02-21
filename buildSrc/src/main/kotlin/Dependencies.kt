object Libs {

    //Retrofit and Moshi convertor - Networking libraries
    val moshi by lazy { "com.squareup.moshi:moshi:${Versions.moshi}" }
    val moshiKotlin by lazy { "com.squareup.moshi:moshi-kotlin:${Versions.moshi}" }
    val retrofit2 by lazy { "com.squareup.retrofit2:retrofit:${Versions.retrofit2}" }
    val retrofit2Converter by lazy { "com.squareup.retrofit2:converter-moshi:${Versions.retrofit2}" }
    val okHttpLogger by lazy { "com.squareup.okhttp3:logging-interceptor:${Versions.okHttpLogging}" }
}

object ClassPathDeps {
    val junit5 by lazy { "de.mannodermaus.gradle.plugins:android-junit5:${Versions.jUnit5GradlePlugin}" }
    val jacoco by lazy { "org.jacoco:org.jacoco.core:${Versions.jacoco}" }
}

object Plugins {
    val junit5 by lazy { "de.mannodermaus.android-junit5" }
    val jacoco_android by lazy { "plugins.jacoco-android" }
}

object TestingLibs {
    val junit4 by lazy { "junit:junit:${Versions.jUnit}" }
    val truth by lazy { "com.google.truth:truth:${Versions.truth}" }
    val junit5JupiterApi by lazy { "org.junit.jupiter:junit-jupiter-api:${Versions.jUnit5}" }
    val junit5JupiterEngine by lazy { "org.junit.jupiter:junit-jupiter-engine:${Versions.jUnit5}" }
    val junit5VintageEngine by lazy { "org.junit.vintage:junit-vintage-engine:${Versions.jUnit5}" }


}