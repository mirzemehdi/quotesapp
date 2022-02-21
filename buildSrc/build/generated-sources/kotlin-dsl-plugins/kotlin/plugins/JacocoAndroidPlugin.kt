package plugins


/**
 * Precompiled [jacoco-android.gradle.kts][plugins.Jacoco_android_gradle] script plugin.
 *
 * @see plugins.Jacoco_android_gradle
 */
class JacocoAndroidPlugin : org.gradle.api.Plugin<org.gradle.api.Project> {
    override fun apply(target: org.gradle.api.Project) {
        try {
            Class
                .forName("plugins.Jacoco_android_gradle")
                .getDeclaredConstructor(org.gradle.api.Project::class.java, org.gradle.api.Project::class.java)
                .newInstance(target, target)
        } catch (e: java.lang.reflect.InvocationTargetException) {
            throw e.targetException
        }
    }
}
