package plugins

import io.gitlab.arturbosch.detekt.extensions.DetektExtension

plugins {
    id("io.gitlab.arturbosch.detekt")
}

detekt {
    config = files("$rootDir/config/detekt/detekt.yml")
    parallel = true
    source = objects.fileCollection()
        .from(
            DetektExtension.DEFAULT_SRC_DIR_JAVA,
            DetektExtension.DEFAULT_SRC_DIR_KOTLIN
        )
}



