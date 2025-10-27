plugins { id("java") apply false }

subprojects {
    apply(plugin = "java")
    repositories { mavenCentral() }
    tasks.withType<JavaCompile> {
        sourceCompatibility = "17"; targetCompatibility = "17"; options.encoding = "UTF-8"
    }
    group = "edu.utexas.ece"; version = "0.1.0-SNAPSHOT"
}


