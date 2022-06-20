import gradle.kotlin.dsl.accessors._1a0c13a14d2120203e3455a82d50f656.testImplementation
import gradle.kotlin.dsl.accessors._1a0c13a14d2120203e3455a82d50f656.testRuntimeOnly
import org.gradle.api.artifacts.dsl.DependencyHandler


private fun DependencyHandler.implement(dependency: Any) {
    add("implementation", dependency)
}

private fun DependencyHandler.testImplement(dependency: Any) {
    add("testImplementation", dependency)
}

fun DependencyHandler.implementNetworkingLibraries() {
    implement(Libs.moshi)
    implement(Libs.moshiKotlin)
    implement(Libs.retrofit2)
    implement(Libs.okHttpLogger)
    implement(Libs.retrofit2Converter)
}

fun DependencyHandler.testImplementJunit5() {
    testImplementation(TestingLibs.junit5JupiterApi)
    testRuntimeOnly(TestingLibs.junit5JupiterEngine)
    testRuntimeOnly(TestingLibs.junit5VintageEngine)
}