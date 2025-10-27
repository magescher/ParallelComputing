subprojects {
    apply(plugin = "java")

    group = "edu.utexas.ece.parallel"
    version = "0.1.0"

    repositories { mavenCentral() }

    tasks.test { useJUnitPlatform() }

    java {
        toolchain { languageVersion.set(JavaLanguageVersion.of(21)) }
    }
}

