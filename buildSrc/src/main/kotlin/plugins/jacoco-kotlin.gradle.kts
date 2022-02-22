package plugins

apply<JacocoPlugin>()

configure<JacocoPluginExtension> {
    this.toolVersion = Versions.jacoco
}

tasks.getByName<JacocoReport>("jacocoTestReport") {
    dependsOn("test")
    reports {
        html.required.set(true)
        xml.required.set(true)
    }
}


