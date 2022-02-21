package plugins


/**
 * Precompiled [jacoco-kotlin.gradle.kts][plugins.Jacoco_kotlin_gradle] script plugin.
 *
 * @see plugins.Jacoco_kotlin_gradle
 */
class JacocoKotlinPlugin : org.gradle.api.Plugin<org.gradle.api.Project> {
    override fun apply(target: org.gradle.api.Project) {
        try {
            Class
                .forName("plugins.Jacoco_kotlin_gradle")
                .getDeclaredConstructor(org.gradle.api.Project::class.java, org.gradle.api.Project::class.java)
                .newInstance(target, target)
        } catch (e: java.lang.reflect.InvocationTargetException) {
            throw e.targetException
        }
    }
}
