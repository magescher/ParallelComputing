plugins { idea }

subprojects {
    // Apply Java plugin early so its extensions/tasks exist
    pluginManager.apply("java")

    group = "edu.utexas.ece"
    version = "0.1.0"

    repositories { mavenCentral() }

    // Configure tests safely (works even if tasks are created later)
    tasks.withType<Test>().configureEach { useJUnitPlatform() }

    // Configure Java toolchain via the Java plugin extension
    extensions.configure<JavaPluginExtension> {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(21))
        }
    }
}

