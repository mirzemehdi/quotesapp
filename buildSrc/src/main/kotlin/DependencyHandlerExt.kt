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