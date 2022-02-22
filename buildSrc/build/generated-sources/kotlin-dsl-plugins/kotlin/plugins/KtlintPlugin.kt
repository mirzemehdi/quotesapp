package plugins


/**
 * Precompiled [ktlint.gradle.kts][plugins.Ktlint_gradle] script plugin.
 *
 * @see plugins.Ktlint_gradle
 */
class KtlintPlugin : org.gradle.api.Plugin<org.gradle.api.Project> {
    override fun apply(target: org.gradle.api.Project) {
        try {
            Class
                .forName("plugins.Ktlint_gradle")
                .getDeclaredConstructor(org.gradle.api.Project::class.java, org.gradle.api.Project::class.java)
                .newInstance(target, target)
        } catch (e: java.lang.reflect.InvocationTargetException) {
            throw e.targetException
        }
    }
}
