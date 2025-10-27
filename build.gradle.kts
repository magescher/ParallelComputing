// No plugins block here.

allprojects {
    repositories { mavenCentral() }
}

subprojects {
    group = "edu.utexas.ece"
    version = "0.1.0-SNAPSHOT"

    tasks.withType<JavaCompile>().configureEach {
        sourceCompatibility = "17"
        targetCompatibility = "17"
        options.encoding = "UTF-8"
    }

    // If a subproject has tests, this makes them use JUnit 5.
    tasks.withType<Test>().configureEach {
        useJUnitPlatform()
    }
}


