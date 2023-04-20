import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.DependencyHandlerScope

private fun DependencyHandler.implement(dependency: Any) {
    add("implementation", dependency)
}
private fun DependencyHandler.debugImplementation(dependency: Any) {
    add("debugImplementation", dependency)
}

private fun DependencyHandler.testImplement(dependency: Any) {
    add("testImplementation", dependency)
}
private fun DependencyHandler.testRuntime(dependency: Any) {
    add("testRuntimeOnly", dependency)
}
private fun DependencyHandler.api(dependency: Any) {
    add("api", dependency)
}


fun DependencyHandler.implementNetworkingLibraries() {
    implement(Libs.moshi)
    implement(Libs.moshiKotlin)
    implement(Libs.retrofit2)
    implement(Libs.okHttpLogger)
    implement(Libs.retrofit2Converter)
}

fun DependencyHandler.implementFirebase() {
    implement(platform(Libs.firebasePlatform))
    implement(Libs.firebaseFireStore)
    implement(Libs.firebaseFireStoreKtx)
    implement(Libs.firebaseCoroutineSupport)
    implement(Libs.firebaseAnalytics)
    implement(Libs.firebaseCrashlytics)
}

fun DependencyHandler.testImplementJunit5() {
    testImplement(TestingLibs.junit5JupiterApi)
    testRuntime(TestingLibs.junit5JupiterEngine)
    testRuntime(TestingLibs.junit5VintageEngine)
}

fun DependencyHandlerScope.implementJetpackComposeUi() {

    implement(platform(Libs.composeBom))
    api(Libs.composeMaterial3)
    api(Libs.composeMaterial2)
    api(Libs.composeFoundation)
    api(Libs.composeUi)
    api(Libs.composeToolingPreview)
    debugImplementation(Libs.composeUiTooling)
    api(Libs.composeLiveData)
    api(Libs.composeLifeCycleRuntime)
    api(Libs.composeLifeCycleViewModel)

}