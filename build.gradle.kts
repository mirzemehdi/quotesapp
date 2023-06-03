import org.apache.tools.ant.taskdefs.condition.Os

// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.google.gms:google-services:4.3.3")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.5.2")
    }
}

tasks.register<Copy>("installGitHook") {
    var suffix="linux"
    if (Os.isFamily(Os.FAMILY_WINDOWS))
        suffix = "windows"

    from(File(rootProject.rootDir, "scripts/pre-commit-$suffix"))
    into { File(rootProject.rootDir, ".git/hooks") }
    rename("pre-commit-$suffix", "pre-commit")
    fileMode = 0b111111111
}

tasks.getByPath(":app:preBuild").dependsOn(tasks.named("installGitHook"))

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}