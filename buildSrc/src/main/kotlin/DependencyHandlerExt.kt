import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

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


fun KotlinDependencyHandler.implementNetworkingLibraries() {

    implementation(Libs.ktorCore)
    implementation(Libs.ktorCioEngine)
    implementation(Libs.ktorResources)
    implementation(Libs.ktorLogging)
    implementation(Libs.ktorSerialization)
    implementation(Libs.ktorContentNegotiation)

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
    implement(Libs.composeMaterial3)
    implement(Libs.composeMaterial2)
    implement(Libs.composeFoundation)
    implement(Libs.composeUi)
    implement(Libs.composeToolingPreview)
    implement(Libs.composeActivity)
    debugImplementation(Libs.composeUiTooling)
    implement(Libs.composeLiveData)
    implement(Libs.composeLifeCycleRuntime)
    implement(Libs.composeLifeCycleViewModel)

}
